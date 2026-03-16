# 測試規格文件 - OrderServiceTest

來源檔案: `src/test/java/com/learning/api/service/OrderServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `OrderService` 的 `createOrder()`、`updateOrder()`、`updateStatus()`、`cancelOrder()`、`payOrder()` 方法。

---

## 前置設定（@BeforeEach）

建立以下測試資料：
- 使用者（role=1）
- 課程（price=500，subject=11）

---

## 測試方法一覽

**總計：11 個測試方法**

### createOrder（4 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `createOrder_validRequest_returnsTrue` | 合法的訂單請求（lessonCount=5） | 回傳 `true` |
| `createOrder_nullUserId_returnsFalse` | userId 為 null | 回傳 `false` |
| `createOrder_zeroLessonCount_returnsFalse` | lessonCount=0 | 回傳 `false` |
| `createOrder_10Lessons_applies95PercentDiscount` | lessonCount=10 | unitPrice=500，discountPrice=475（95 折） |

### updateOrder（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `updateOrder_validRequest_returnsTrue` | 更新 pending 訂單的 lessonCount | 回傳 `true` |
| `updateOrder_completeOrder_returnsFalse` | 嘗試更新 status=3（已完成）的訂單 | 回傳 `false` |

### updateStatus（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `updateStatus_forward_returnsTrue` | status 從 1（pending）→ 2（deal） | 回傳 `true` |
| `updateStatus_backward_returnsFalse` | status 從 2（deal）→ 1（pending，退回） | 回傳 `false` |

### cancelOrder（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `cancelOrder_pendingOrder_returnsTrue` | 取消 status=1（pending）的訂單 | 回傳 `true`，訂單從資料庫刪除 |
| `cancelOrder_paidOrder_returnsFalse` | 嘗試取消 status=2（deal）的訂單 | 回傳 `false` |

### payOrder（2 個測試，但實際含 1 個，所以以下為 2 個獨立的方法）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `payOrder_pendingOrder_returnsTrue` | 支付 status=1（pending）的訂單 | 回傳 `true`，status 更新為 2 |
| `payOrder_paidOrder_returnsFalse` | 嘗試支付 status=2（已支付）的訂單 | 回傳 `false` |
