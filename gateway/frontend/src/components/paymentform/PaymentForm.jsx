import React, { useState, useMemo } from "react";
import "./PaymentForm.css";
import { useMutation, useQuery } from "@tanstack/react-query";
import { gatewayClient, tokenizerClient } from "../../api/axiosClient";

const CURRENCY_SYMBOLS = {
  USD: "$",
  EUR: "€",
  GBP: "£",
  INR: "₹",
  AUD: "A$",
  CAD: "C$",
  SGD: "S$",
  AED: "د.إ",
  JPY: "¥",
};

function getSessionIdFromUrl() {
  const pathSegments = window.location.pathname.split("/").filter(Boolean);
  return pathSegments[pathSegments.length - 1] || "";
}

async function fetchSessionDetails(sessionId) {
  const { data } = await gatewayClient.get("/gateway/get_session", {
    params: { sessionId },
    headerProfile: "none", // source request — captures headers, doesn't send them
  });
  return data;
}

function formatAmount(amount, currency) {
  const symbol = CURRENCY_SYMBOLS[currency] || currency + " ";
  const num = parseFloat(amount);
  if (isNaN(num)) return `${symbol}0.00`;
  // JPY has no decimal places
  const decimals = currency === "JPY" ? 0 : 2;
  return `${symbol}${num.toLocaleString(undefined, { minimumFractionDigits: decimals, maximumFractionDigits: decimals })}`;
}

const COUNTRY_CODES = [
  { code: "+91", label: "🇮🇳 +91" },
  { code: "+1", label: "🇺🇸 +1" },
  { code: "+44", label: "🇬🇧 +44" },
  { code: "+61", label: "🇦🇺 +61" },
  { code: "+971", label: "🇦🇪 +971" },
  { code: "+65", label: "🇸🇬 +65" },
  { code: "+49", label: "🇩🇪 +49" },
  { code: "+33", label: "🇫🇷 +33" },
  { code: "+81", label: "🇯🇵 +81" },
];

const COUNTRIES = [
  "India",
  "United States",
  "United Kingdom",
  "Australia",
  "Canada",
  "Germany",
  "France",
  "Japan",
  "Singapore",
  "UAE",
];

const DEFAULT_PAYMENT_AMOUNT = "100";
const DEFAULT_PAYMENT_CURRENCY = "USD";

function LockIcon() {
  return (
    <svg
      width="14"
      height="14"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2.5"
    >
      <rect x="3" y="11" width="18" height="11" rx="2" />
      <path d="M7 11V7a5 5 0 0 1 10 0v4" />
    </svg>
  );
}

function CheckIcon() {
  return (
    <svg
      width="30"
      height="30"
      viewBox="0 0 24 24"
      fill="none"
      stroke="#059669"
      strokeWidth="2.5"
    >
      <polyline points="20 6 9 17 4 12" />
    </svg>
  );
}

function CreditIcon() {
  return (
    <svg
      width="14"
      height="14"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
    >
      <rect x="1" y="4" width="22" height="16" rx="2" />
      <line x1="1" y1="10" x2="23" y2="10" />
    </svg>
  );
}

function DebitIcon() {
  return (
    <svg
      width="14"
      height="14"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
    >
      <rect x="1" y="4" width="22" height="16" rx="2" />
      <circle cx="7" cy="15" r="2" />
    </svg>
  );
}

export default function PaymentForm() {
  const sessionId = useMemo(() => getSessionIdFromUrl(), []);

  const { data: sessionData, isLoading: sessionLoading, isError: sessionError } = useQuery({
    queryKey: ["session", sessionId],
    queryFn: () => fetchSessionDetails(sessionId),
    enabled: !!sessionId,
    retry: 2,
    staleTime: 10000,
  });

  const amount = sessionData?.amount ?? DEFAULT_PAYMENT_AMOUNT;
  const currency = (sessionData?.currency ?? DEFAULT_PAYMENT_CURRENCY).toUpperCase();
  const formattedAmount = useMemo(() => formatAmount(amount / 100, currency), [amount, currency]);

  const [tab, setTab] = useState("credit");
  const [cvvFocus, setCvvFocus] = useState(false);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [btnError, setBtnError] = useState(false);

  const [form, setForm] = useState({
    name: "test",
    number: "4242 4242 4242 4242",
    expiry: "12/35",
    cvv: "123",
    phone: "",
    countryCode: "+91",
    address1: "",
    address2: "",
    city: "",
    state: "",
    zip: "",
    country: "India",
  });

  const [errors, setErrors] = useState({});

  const set = (field) => (e) => {
    setForm((f) => ({ ...f, [field]: e.target.value }));
    setErrors((er) => ({ ...er, [field]: false }));
  };

  const formatNumber = (e) => {
    let v = e.target.value.replace(/\D/g, "").slice(0, 16);
    let fmt = v.replace(/(.{4})/g, "$1 ").trim();
    setForm((f) => ({ ...f, number: fmt }));
    setErrors((er) => ({ ...er, number: false }));
  };

  const formatExpiry = (e) => {
    let raw = e.target.value.replace(/\D/g, "").slice(0, 4);
    let val = raw;
    if (raw.length >= 3) val = raw.slice(0, 2) + " / " + raw.slice(2);
    else if (raw.length === 2 && e.target.value.length > 2) val = raw + " / ";
    setForm((f) => ({ ...f, expiry: val }));
    setErrors((er) => ({ ...er, expiry: false }));
  };

  const formatPhone = (e) => {
    let v = e.target.value.replace(/\D/g, "").slice(0, 12);
    setForm((f) => ({ ...f, phone: v }));
    setErrors((er) => ({ ...er, phone: false }));
  };

  const validate = () => {
    const errs = {};
    if (!form.name.trim()) errs.name = true;
    if (form.number.replace(/\s/g, "").length < 15) errs.number = true;
    if (form.expiry.length < 5) errs.expiry = true;
    if (form.cvv.length < 3) errs.cvv = true;
    // if (form.phone.length < 7) errs.phone = true;
    // if (!form.address1.trim()) errs.address1 = true;
    // if (!form.city.trim()) errs.city = true;
    // if (!form.state.trim()) errs.state = true;
    // if (!form.zip.trim()) errs.zip = true;
    return errs;
  };


  const createCardToken = async () => {
    const { data } = await tokenizerClient.post(
      "/orbyte/tokenizer/api/v1/cardtoken/orb_tokenize",
      {
        cardNumber: form.number,
        cvv: form.cvv,
        expiryMonth: form.expiry.split("/")[0].trim(),
        expiryYear: form.expiry.split("/")[1].trim(),
      },
      { headerProfile: "tokenizer" }
    );
    return data;
  };

  const createStripePayment = async (tokenData) => {

    /*
    
    {
  "cardNumber": "4242424XXXXXXX4242",
  "expiryMonth": 12,
  "expiryYear": 34,
  "token": "2VLGcMKdab2ES3sJvULRMapc6+drMZ270l9HPDufWv0qYEIbSmSS28mzp3AKQGZ/HEvVP275S3nVBO838M7r+Qa2bUOvoNZGet6AMafhUJ0wRCVJFczvhcQweQh6rrvk0jvOQCkRWUlX+A==",
  "binDetails": {
    "number": {
      "length": null,
      "luhn": null
    },
    "scheme": "visa",
    "type": "credit",
    "brand": "Visa Classic",
    "country": {
      "numeric": "826",
      "alpha2": "GB",
      "name": "United Kingdom of Great Britain and Northern Ireland (the)",
      "emoji": "🇬🇧",
      "currency": "GBP",
      "latitude": 54,
      "longitude": -2
    },
    "bank": {
      "name": "Stripe Payments Uk Limited"
    }
  }
}
    */


    const token = tokenData.token;
    const binDetails = tokenData.binDetails;
    const { data } = await gatewayClient.post(
      "/gateway/initiate_payment",
      {
        paymentType: "CARD",
        amount: amount,
        currency,
        paymentMethodDetails: {
          cardToken: token,
          binInfo: {
            scheme: binDetails.scheme,
            cardType: binDetails.type,
            brand: binDetails.brand,
            country: binDetails.country.alpha2,
            currency: binDetails.country.currency,
            bank: binDetails.bank.name
          }
        }
      },
      { headerProfile: "gateway" }
    );
    return data;
  };

  const mutation = useMutation({
    mutationFn: createCardToken,
  });

  const handlePay = async () => {
    const errs = validate();
    if (Object.keys(errs).length > 0) {
      setErrors(errs);
      setBtnError(true);
      setTimeout(() => setBtnError(false), 1800);
      return;
    }
    setLoading(true);

    try {
      const tokenData = await mutation.mutateAsync();
      await createStripePayment(tokenData);

      setSuccess(true);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const reset = () => {
    setSuccess(false);
    setForm({
      name: "",
      number: "",
      expiry: "",
      cvv: "",
      phone: "",
      countryCode: "+91",
      address1: "",
      address2: "",
      city: "",
      state: "",
      zip: "",
      country: "India",
    });
    setErrors({});
  };

  const numDisplay = (() => {
    let raw = form.number.replace(/\s/g, "").padEnd(16, "•");
    return [0, 4, 8, 12].map((i) => raw.slice(i, i + 4)).join("  ");
  })();

  return (
    <>
      <div className="pf-root">
        <div className="pf-container">
          <div className="pf-header">
            <h1>Secure Checkout</h1>
            <p>Complete your purchase safely and instantly</p>
          </div>

          {/* Session Loading / Error States */}
          {sessionLoading && (
            <div className="pf-amount-banner pf-amount-loading">
              <div className="pf-amount-label">Loading session…</div>
              <div className="pf-amount-value">
                <div className="pf-skeleton" style={{ width: 120, height: 28, borderRadius: 8 }} />
              </div>
            </div>
          )}

          {sessionError && (
            <div className="pf-amount-banner pf-amount-error">
              <div className="pf-amount-label">⚠ Unable to load payment details</div>
            </div>
          )}

          {/* Amount Display */}
          {!sessionLoading && !sessionError && (
            <div className="pf-amount-banner">
              <div className="pf-amount-label">Amount to pay</div>
              <div className="pf-amount-value">{formattedAmount}</div>
              <div className="pf-amount-currency">{currency}</div>
            </div>
          )}

          {/* Card Preview */}
          <div className={`pf-card-preview${cvvFocus ? " cvv-focus" : ""}`}>
            <div className="pf-card-orb1" />
            <div className="pf-card-orb2" />
            <div className="pf-card-top">
              <div className="pf-chip" />
              <div className="pf-network">
                <div className="pf-circle pf-circle-r" />
                <div className="pf-circle pf-circle-o" />
              </div>
            </div>
            <div className="pf-card-number">{numDisplay}</div>
            <div className="pf-card-bottom">
              <div>
                <div className="pf-card-label">cardholder</div>
                <div className="pf-card-value">{form.name || "Full Name"}</div>
              </div>
              <div>
                <div className="pf-card-label">expires</div>
                <div className="pf-card-value">{form.expiry || "MM / YY"}</div>
              </div>
            </div>
          </div>

          {/* Form */}
          <div className="pf-form-card">
            {success ? (
              <div className="pf-success">
                <div className="pf-success-circle">
                  <CheckIcon />
                </div>
                <h3>Payment successful!</h3>
                <p>
                  Your transaction has been processed securely. A confirmation
                  has been sent to your details.
                </p>
                <button className="pf-success-btn" onClick={reset}>
                  Make another payment
                </button>
              </div>
            ) : (
              <>
                {/* Tabs */}
                <div className="pf-tabs">
                  <button
                    className={`pf-tab${tab === "credit" ? " active" : ""}`}
                    onClick={() => setTab("credit")}
                  >
                    <CreditIcon /> Credit card
                  </button>
                  <button
                    className={`pf-tab${tab === "debit" ? " active" : ""}`}
                    onClick={() => setTab("debit")}
                  >
                    <DebitIcon /> Debit card
                  </button>
                </div>

                {/* Card brands */}
                <div className="pf-brands">
                  {["Visa", "Mastercard", "Amex", "Discover", "Rupay"].map(
                    (b) => (
                      <div className="pf-brand" key={b}>
                        {b}
                      </div>
                    ),
                  )}
                </div>

                {/* ── Card Details ── */}
                <div className="pf-section-label">Card details</div>

                <div className="pf-field">
                  <label>Cardholder name</label>
                  <input
                    type="text"
                    placeholder="Full name as on card"
                    value={form.name}
                    onChange={set("name")}
                    className={errors.name ? "error" : ""}
                    autoComplete="cc-name"
                  />
                </div>

                <div className="pf-field">
                  <label>Card number</label>
                  <input
                    type="text"
                    className={`mono${errors.number ? " error" : ""}`}
                    placeholder="0000 0000 0000 0000"
                    value={form.number}
                    onChange={formatNumber}
                    maxLength={19}
                    inputMode="numeric"
                    autoComplete="cc-number"
                  />
                </div>

                <div className="pf-row">
                  <div className="pf-field">
                    <label>Expiry date</label>
                    <input
                      type="text"
                      className={`mono${errors.expiry ? " error" : ""}`}
                      placeholder="MM / YY"
                      value={form.expiry}
                      onChange={formatExpiry}
                      maxLength={7}
                      inputMode="numeric"
                      autoComplete="cc-exp"
                    />
                  </div>
                  <div className="pf-field">
                    <label>CVV / CVC</label>
                    <input
                      type="password"
                      className={`mono${errors.cvv ? " error" : ""}`}
                      placeholder="•••"
                      value={form.cvv}
                      onChange={set("cvv")}
                      maxLength={4}
                      inputMode="numeric"
                      autoComplete="cc-csc"
                      onFocus={() => setCvvFocus(true)}
                      onBlur={() => setCvvFocus(false)}
                    />
                  </div>
                </div>

                {/* ── Contact ── */}
                <div className="pf-section-label">Contact</div>

                <div className="pf-field">
                  <label>Phone number</label>
                  <div className="pf-phone-row">
                    <div className="pf-field" style={{ marginBottom: 0 }}>
                      <select
                        value={form.countryCode}
                        onChange={set("countryCode")}
                      >
                        {COUNTRY_CODES.map((c) => (
                          <option key={c.code} value={c.code}>
                            {c.label}
                          </option>
                        ))}
                      </select>
                    </div>
                    <div className="pf-field" style={{ marginBottom: 0 }}>
                      <input
                        type="tel"
                        placeholder="9876543210"
                        value={form.phone}
                        onChange={formatPhone}
                        className={errors.phone ? "error" : ""}
                        inputMode="numeric"
                        autoComplete="tel"
                      />
                    </div>
                  </div>
                </div>

                {/* ── Billing Address ── */}
                <div className="pf-section-label">Billing address</div>

                <div className="pf-field">
                  <label>Address line 1</label>
                  <input
                    type="text"
                    placeholder="Street address, flat, house no."
                    value={form.address1}
                    onChange={set("address1")}
                    className={errors.address1 ? "error" : ""}
                    autoComplete="address-line1"
                  />
                </div>

                <div className="pf-field">
                  <label>
                    Address line 2{" "}
                    <span style={{ color: "#ccc", fontWeight: 400 }}>
                      (optional)
                    </span>
                  </label>
                  <input
                    type="text"
                    placeholder="Apartment, suite, landmark"
                    value={form.address2}
                    onChange={set("address2")}
                    autoComplete="address-line2"
                  />
                </div>

                <div className="pf-row">
                  <div className="pf-field">
                    <label>City</label>
                    <input
                      type="text"
                      placeholder="Mumbai"
                      value={form.city}
                      onChange={set("city")}
                      className={errors.city ? "error" : ""}
                      autoComplete="address-level2"
                    />
                  </div>
                  <div className="pf-field">
                    <label>State / Province</label>
                    <input
                      type="text"
                      placeholder="Maharashtra"
                      value={form.state}
                      onChange={set("state")}
                      className={errors.state ? "error" : ""}
                      autoComplete="address-level1"
                    />
                  </div>
                </div>

                <div className="pf-row">
                  <div className="pf-field">
                    <label>ZIP / Postal code</label>
                    <input
                      type="text"
                      placeholder="411018"
                      value={form.zip}
                      onChange={set("zip")}
                      className={errors.zip ? "error" : ""}
                      inputMode="numeric"
                      maxLength={10}
                      autoComplete="postal-code"
                    />
                  </div>
                  <div className="pf-field">
                    <label>Country</label>
                    <select
                      value={form.country}
                      onChange={set("country")}
                      autoComplete="country-name"
                    >
                      {COUNTRIES.map((c) => (
                        <option key={c}>{c}</option>
                      ))}
                    </select>
                  </div>
                </div>

                {/* Security */}
                <div className="pf-security">
                  <LockIcon />
                  <span>
                    256-bit SSL encrypted — your data is fully protected
                  </span>
                </div>

                {/* Pay Button */}
                <button
                  className={`pf-pay-btn${btnError ? " error-state" : ""}`}
                  onClick={handlePay}
                  disabled={loading}
                >
                  {loading ? (
                    <>
                      <div className="pf-spinner" /> Processing...
                    </>
                  ) : btnError ? (
                    "Please fill all required fields"
                  ) : (
                    <>
                      <LockIcon /> Pay {formattedAmount}
                    </>
                  )}
                </button>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
