# 測試規格文件 - CheckoutServiceTest

來源檔案: `src/test/java/com/learning/api/service/CheckoutServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `CheckoutService.processPurchase()` 方法，驗證購買並預約流程的完整性、餘額扣除及各種失敗情境。

---

## 前置設定（@BeforeEach）

建立以下測試資料：
- **有錢的學生**（wallet=10000）
- **沒錢的學生**（wallet=0）
- **老師**（role=2）
- **課程**（price=500）
- **老師課表**（下週一 10:00，status="available"）

---

## 測試方法一覽

**總計：5 個測試方法**

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `processPurchase_validSlot_returnsSuccess` | 有錢學生 + 可用時段 | 回傳 `"success"` |
| `processPurchase_insufficientBalance_returns餘額不足` | 沒錢學生 + 可用時段 | 回傳 `"餘額不足"` |
| `processPurchase_noSchedule_returnsErrorMessage` | 不存在的時段（23:00） | 回傳非空字串，且不為 `"success"` 或 `"餘額不足"` |
| `processPurchase_alreadyBooked_returnsErrorMessage` | 時段已被既有預約佔用 | 回傳非空字串，且不為 `"success"` |
| `processPurchase_success_deductsWallet` | 成功購買後檢查錢包 | 學生 wallet 從 10000 扣除 500，剩餘 9500 |
