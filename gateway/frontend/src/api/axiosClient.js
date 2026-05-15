import axios from "axios";
import { captureFromResponse, getHeadersForProfile } from "./headerStore";

const gatewayClient = axios.create({
  baseURL: "http://localhost:5003",
  withCredentials: true,
});

// Response interceptor: capture configured fields from response body
gatewayClient.interceptors.response.use((response) => {
  if (response.data && typeof response.data === "object") {
    captureFromResponse(response.data);
  }
  return response;
});

// Request interceptor: attach stored headers based on profile
// Set `headerProfile` in request config to control which headers are sent
// e.g. gatewayClient.get("/url", { headerProfile: "gateway" })
gatewayClient.interceptors.request.use((config) => {
  const profile = config.headerProfile || "none";
  const headers = getHeadersForProfile(profile);
  Object.entries(headers).forEach(([key, value]) => {
    config.headers[key] = value;
  });
  return config;
});

// Separate instance for tokenizer (different origin)
const tokenizerClient = axios.create({
  baseURL: "http://localhost:5002",
  withCredentials: true,
});

// Same request interceptor for tokenizer
tokenizerClient.interceptors.request.use((config) => {
  const profile = config.headerProfile || "none";
  const headers = getHeadersForProfile(profile);
  Object.entries(headers).forEach(([key, value]) => {
    config.headers[key] = value;
  });
  return config;
});

export { gatewayClient, tokenizerClient };
