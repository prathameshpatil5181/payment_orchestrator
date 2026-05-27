-- =============================================
-- Rule 1: Visa high value credit international
-- Expected: 1 row → WORLDPAY
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'USD')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'US')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'VISA')
  AND (amount_min  IS NULL OR amount_min  <= 15000)
  AND (amount_max  IS NULL OR amount_max  >= 15000)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 1, Rule 10 (catch-all)
-- First row (lowest priority number) = WORLDPAY ✓

-- =============================================
-- Rule 2: Mastercard GBP UK
-- Expected: 1st row → WORLDPAY
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'GBP')
  AND (card_type   IS NULL OR card_type   = 'DEBIT')
  AND (country     IS NULL OR country     = 'GB')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'MASTERCARD')
  AND (amount_min  IS NULL OR amount_min  <= 800)
  AND (amount_max  IS NULL OR amount_max  >= 800)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 2, Rule 10
-- First row = WORLDPAY ✓

-- =============================================
-- Rule 3: Amex high value (amount >= 5000)
-- Expected: 1st row → WORLDPAY
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'EUR')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'DE')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'AMEX')
  AND (amount_min  IS NULL OR amount_min  <= 8000)
  AND (amount_max  IS NULL OR amount_max  >= 8000)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 3, Rule 10
-- First row = WORLDPAY ✓

-- =============================================
-- Rule 4: Visa low value USD debit (amount <= 500)
-- Expected: 1st row → STRIPE
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'USD')
  AND (card_type   IS NULL OR card_type   = 'DEBIT')
  AND (country     IS NULL OR country     = 'US')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'VISA')
  AND (amount_min  IS NULL OR amount_min  <= 200)
  AND (amount_max  IS NULL OR amount_max  >= 200)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 4, Rule 10
-- First row = STRIPE ✓

-- =============================================
-- Rule 5: Mastercard EUR travel
-- Expected: 1st row → WORLDPAY
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'EUR')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'FR')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'MASTERCARD')
  AND (amount_min  IS NULL OR amount_min  <= 1200)
  AND (amount_max  IS NULL OR amount_max  >= 1200)
  AND (merchant_category IS NULL OR merchant_category = 'TRAVEL')
ORDER BY priority ASC;
-- Should match: Rule 5, Rule 10
-- First row = WORLDPAY ✓

-- =============================================
-- Rule 6: Maestro debit any currency
-- Expected: 1st row → WORLDPAY
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'EUR')
  AND (card_type   IS NULL OR card_type   = 'DEBIT')
  AND (country     IS NULL OR country     = 'NL')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'MAESTRO')
  AND (amount_min  IS NULL OR amount_min  <= 300)
  AND (amount_max  IS NULL OR amount_max  >= 300)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 6, Rule 10
-- First row = WORLDPAY ✓

-- =============================================
-- Rule 7: Visa EUR mobile channel
-- Expected: 1st row → STRIPE
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'EUR')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'ES')
  AND (channel     IS NULL OR channel     = 'MOBILE')
  AND (bin_brand   IS NULL OR bin_brand   = 'VISA')
  AND (amount_min  IS NULL OR amount_min  <= 750)
  AND (amount_max  IS NULL OR amount_max  >= 750)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 7, Rule 10
-- First row = STRIPE ✓

-- =============================================
-- Rule 8: Amex low value (amount < 5000)
-- Expected: 1st row → STRIPE
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'USD')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'US')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'AMEX')
  AND (amount_min  IS NULL OR amount_min  <= 2000)
  AND (amount_max  IS NULL OR amount_max  >= 2000)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 8, Rule 10
-- First row = STRIPE ✓

-- =============================================
-- Rule 9: Mastercard USD API high value
-- Expected: 1st row → WORLDPAY
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'USD')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'US')
  AND (channel     IS NULL OR channel     = 'API')
  AND (bin_brand   IS NULL OR bin_brand   = 'MASTERCARD')
  AND (amount_min  IS NULL OR amount_min  <= 7000)
  AND (amount_max  IS NULL OR amount_max  >= 7000)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 9, Rule 10
-- First row = WORLDPAY ✓

-- =============================================
-- Rule 10: Catch-all (DISCOVER — no specific rule)
-- Expected: 1 row → STRIPE
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency    IS NULL OR currency    = 'USD')
  AND (card_type   IS NULL OR card_type   = 'CREDIT')
  AND (country     IS NULL OR country     = 'US')
  AND (channel     IS NULL OR channel     = 'WEB')
  AND (bin_brand   IS NULL OR bin_brand   = 'DISCOVER')
  AND (amount_min  IS NULL OR amount_min  <= 500)
  AND (amount_max  IS NULL OR amount_max  >= 500)
  AND (merchant_category IS NULL OR merchant_category = 'RETAIL')
ORDER BY priority ASC;
-- Should match: Rule 10 only
-- First row = STRIPE ✓





-- =============================================
-- Edge 1: Amex exactly at the 5000 boundary
-- Rule 3 needs amount >= 5000, Rule 8 needs amount <= 4999.99
-- At exactly 5000 → should match Rule 3 → WORLDPAY
-- =============================================
SELECT id, name, priority, processor, amount_min, amount_max
FROM routing_rules
WHERE active = TRUE
  AND (bin_brand  IS NULL OR bin_brand  = 'AMEX')
  AND (card_type  IS NULL OR card_type  = 'CREDIT')
  AND (amount_min IS NULL OR amount_min <= 5000.00)
  AND (amount_max IS NULL OR amount_max >= 5000.00)
ORDER BY priority ASC;
-- Expected: Rule 3 (WORLDPAY) appears before Rule 8 (STRIPE)
-- Rule 8 max = 4999.99 so it should NOT appear here ✓

-- =============================================
-- Edge 2: Amex at 4999.99 — just below boundary
-- Should match Rule 8 → STRIPE, not Rule 3
-- =============================================
SELECT id, name, priority, processor, amount_min, amount_max
FROM routing_rules
WHERE active = TRUE
  AND (bin_brand  IS NULL OR bin_brand  = 'AMEX')
  AND (card_type  IS NULL OR card_type  = 'CREDIT')
  AND (amount_min IS NULL OR amount_min <= 4999.99)
  AND (amount_max IS NULL OR amount_max >= 4999.99)
ORDER BY priority ASC;
-- Expected: Rule 8 (STRIPE), Rule 10 (STRIPE)
-- Rule 3 should NOT appear (amount_min = 5000 > 4999.99) ✓

-- =============================================
-- Edge 3: Visa credit EUR on WEB vs MOBILE
-- Same card/currency but different channel → different rule
-- WEB → should NOT match Rule 7 (MOBILE only)
-- =============================================
SELECT id, name, priority, processor, channel
FROM routing_rules
WHERE active = TRUE
  AND (currency  IS NULL OR currency  = 'EUR')
  AND (card_type IS NULL OR card_type = 'CREDIT')
  AND (bin_brand IS NULL OR bin_brand = 'VISA')
  AND (channel   IS NULL OR channel   = 'WEB')       -- WEB channel
  AND (amount_min IS NULL OR amount_min <= 500)
  AND (amount_max IS NULL OR amount_max >= 500)
ORDER BY priority ASC;
-- Rule 7 has channel='MOBILE' so should NOT appear
-- Expected: Rule 1 (if amount >= 10k) or Rule 10 only → STRIPE ✓

-- =============================================
-- Edge 4: Mastercard EUR TRAVEL vs RETAIL
-- Rule 5 is TRAVEL only — RETAIL txn should skip it
-- =============================================
SELECT id, name, priority, processor, merchant_category
FROM routing_rules
WHERE active = TRUE
  AND (currency           IS NULL OR currency           = 'EUR')
  AND (bin_brand          IS NULL OR bin_brand          = 'MASTERCARD')
  AND (merchant_category  IS NULL OR merchant_category  = 'RETAIL')  -- not TRAVEL
  AND (amount_min         IS NULL OR amount_min         <= 1200)
  AND (amount_max         IS NULL OR amount_max         >= 1200)
ORDER BY priority ASC;
-- Rule 5 has merchant_category='TRAVEL' so should NOT appear
-- Expected: Rule 2 (if GBP+GB), else Rule 10 → STRIPE ✓

-- =============================================
-- Edge 5: No bin_brand known (NULL bin_brand on transaction)
-- Only catch-all should fire
-- =============================================
SELECT id, name, priority, processor
FROM routing_rules
WHERE active = TRUE
  AND (currency  IS NULL OR currency  = 'USD')
  AND (card_type IS NULL OR card_type = 'CREDIT')
  AND (channel   IS NULL OR channel   = 'WEB')
  AND (bin_brand IS NULL OR bin_brand = NULL)   -- unknown brand
  AND (amount_min IS NULL OR amount_min <= 300)
  AND (amount_max IS NULL OR amount_max >= 300)
ORDER BY priority ASC;
-- bin_brand = NULL on txn means: only rules where bin_brand IS NULL match
-- Expected: Rule 10 only → STRIPE ✓



-- How many active rules exist per processor
SELECT processor, COUNT(*) AS rule_count
FROM routing_rules
WHERE active = TRUE
GROUP BY processor
ORDER BY rule_count DESC;
-- Expected:
-- WORLDPAY  5   (rules 1,2,3,5,6,9)
-- STRIPE    5   (rules 4,7,8,10 + one more)

-- How many rules have each bin_brand
SELECT COALESCE(bin_brand, '(wildcard)') AS bin_brand,
       COUNT(*) AS rule_count
FROM routing_rules
WHERE active = TRUE
GROUP BY bin_brand
ORDER BY rule_count DESC;
-- Expected:
-- VISA        3  (rules 1,4,7)
-- MASTERCARD  3  (rules 2,5,9)
-- AMEX        2  (rules 3,8)
-- MAESTRO     1  (rule 6)
-- (wildcard)  1  (rule 10 catch-all)

-- Confirm no duplicate priorities
SELECT priority, COUNT(*) AS cnt
FROM routing_rules
WHERE active = TRUE
GROUP BY priority
HAVING COUNT(*) > 1;
-- Expected: 0 rows ✓

-- Full rule list ordered by priority — quick visual check
SELECT priority, name, currency, card_type, country,
       bin_brand, amount_min, amount_max,
       channel, merchant_category, processor
FROM routing_rules
WHERE active = TRUE
ORDER BY priority ASC;