/**
 * Payment Session Header Store (in-memory)
 *
 * Captures sessionId and transactionId from response body,
 * stores in memory, and forwards them as request headers.
 *
 * ── HOW TO EXTEND ──
 * 1. To capture a new field from response body → add entry to CAPTURE_MAP
 * 2. To control which headers go to which request → edit REQUEST_PROFILES
 */

// ─────────────────────────────────────────────────────────
// CAPTURE_MAP: response body field → header name to store
// ─────────────────────────────────────────────────────────
const CAPTURE_MAP = {
  sessionId: "SessionId",
  transactionId: "TransactionId",
  // Example future additions:
  // merchantId: "X-Merchant-Id",
};

// ─────────────────────────────────────────────────────────
// REQUEST_PROFILES: which headers to send per request type
// Keys are profile names used in axios config: { headerProfile: "gateway" }
// ─────────────────────────────────────────────────────────
const REQUEST_PROFILES = {
  gateway: ["SessionId", "TransactionId"],
  tokenizer: ["SessionId"],
  all: Object.values(CAPTURE_MAP),
  none: [],
};

// ── In-memory store ──
let headerStore = {};

function save(headers) {
  headerStore = { ...headerStore, ...headers };
}

function load() {
  return { ...headerStore };
}

function clear() {
  headerStore = {};
}

// ── Public API ──

/**
 * Extract configured fields from a response body and persist them in memory.
 * Called from response interceptor.
 *
 * @param {Object} responseData - parsed response body
 * @returns {Object} headers that were captured
 */
function captureFromResponse(responseData) {
  if (!responseData || typeof responseData !== "object") return {};

  const captured = {};
  Object.entries(CAPTURE_MAP).forEach(([bodyField, headerName]) => {
    if (responseData[bodyField] != null) {
      captured[headerName] = String(responseData[bodyField]);
    }
  });

  if (Object.keys(captured).length > 0) {
    save(captured);
  }

  return captured;
}

/**
 * Get stored headers filtered by request profile.
 *
 * @param {string} profileName - key from REQUEST_PROFILES
 * @returns {Object} headers to attach
 */
function getHeadersForProfile(profileName = "all") {
  const stored = load();
  const allowed = REQUEST_PROFILES[profileName];

  if (!allowed) {
    console.warn(`[HeaderStore] Unknown profile "${profileName}", sending no headers.`);
    return {};
  }

  const headers = {};
  allowed.forEach((key) => {
    if (stored[key] != null) {
      headers[key] = stored[key];
    }
  });

  return headers;
}

/**
 * Manually set a header value.
 *
 * @param {string} key - Header name
 * @param {string} value - Header value
 */
function setHeader(key, value) {
  headerStore[key] = String(value);
}

export { captureFromResponse, getHeadersForProfile, setHeader, clear, load };
