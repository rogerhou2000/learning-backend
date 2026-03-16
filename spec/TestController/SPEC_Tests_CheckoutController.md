# 測試規格文件 - CheckoutControllerTest

來源檔案: `src/test/java/com/learning/api/controller/CheckoutControllerTest.java`

測試類型: 整合測試（`@SpringBootTest` + MockMvc）

---

## 概述

測試 `CheckoutController` 的 `/api/shop/purchase` 端點，驗證購買並預約流程的各種情境。

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

**總計：4 個測試方法**

### POST /api/shop/purchase（4 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `purchase_validRequest_shouldReturn200` | 有錢學生、課表可用時段 | `200 OK` | `{ "msg": "購買並預約成功！" }` |
| `purchase_insufficientBalance_shouldReturn402` | 沒錢學生、課表可用時段 | `402 Payment Required` | `{ "msg": "餘額不足", "action": "recharge" }` |
| `purchase_unavailableSlot_shouldReturn400` | 有錢學生、不存在的時段（23:00） | `400 Bad Request` | `{ "msg": "（非空錯誤訊息）" }` |
| `purchase_alreadyBooked_shouldReturn400` | 有錢學生、但時段已被預約 | `400 Bad Request` | `{ "msg": "（非空錯誤訊息）" }` |
