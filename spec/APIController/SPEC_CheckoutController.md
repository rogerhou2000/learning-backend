# 規格文件 - CheckoutController

來源檔案: `src/main/java/com/learning/api/controller/CheckoutController.java`

Base URL: `http://localhost:8080`

---

## 概述

處理課程購買並預約的一站式 API。一次請求完成付款與排課預約，底層由 `CheckoutService.processPurchase()` 以 `@Transactional` 確保原子性。

---

## API 互動邏輯

### 1. 購買並預約課程

* **請求資訊（HTTP Request）**
- Method: `POST`
- URL: `/api/shop/purchase`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{
  "studentId": 1,
  "courseId": 2,
  "selectedSlots": [
    { "date": "2026-03-23", "hour": 10 },
    { "date": "2026-03-24", "hour": 14 }
  ]
}
```

| 欄位 | 型別 | 必填 | 說明 |
|---|---|---|---|
| `studentId` | Long | 是 | 學生的 User ID |
| `courseId` | Long | 是 | 要購買的課程 ID |
| `selectedSlots` | Array | 是 | 欲預約的時段清單 |
| `selectedSlots[].date` | LocalDate | 是 | 預約日期（格式：YYYY-MM-DD） |
| `selectedSlots[].hour` | Integer | 是 | 預約小時（24 小時制，例如 10 代表 10:00） |

* **回應內容 (Response)**

| HTTP Status | 情境 | Body |
|---|---|---|
| `200 OK` | 購買並預約成功 | `{ "msg": "購買並預約成功！" }` |
| `402 Payment Required` | 學生錢包餘額不足 | `{ "msg": "餘額不足", "action": "recharge" }` |
| `400 Bad Request` | 時段不可用、已被預約或其他驗證失敗 | `{ "msg": "（錯誤原因）" }` |

---

## 核心業務邏輯（CheckoutService.processPurchase）

```
@Transactional 原子交易：
  1. 查詢課程，計算金額（課程單價 × 時段數）
  2. 驗證學生錢包餘額 → 不足則回傳 "餘額不足"
  3. 逐一驗證 selectedSlots：
     - 查詢 TutorSchedule，確認該時段為 "available"
     - 查詢 Bookings，確認無衝突預約
  4. 建立 Order（status=1 pending）
  5. 逐一建立 Bookings（關聯 orderId）
  6. 更新 TutorSchedule status → "booked"
  7. 扣除學生 wallet
  8. 建立 WalletLog（transactionType=2 購課）
  → 任何步驟失敗則整筆 rollback
```

---

## 資料庫欄位說明

| 實體 | 資料表 | 說明 |
|---|---|---|
| `User` | `users` | 學生（扣款）與老師（收款來源） |
| `Course` | `courses` | 課程資訊（單價） |
| `TutorSchedule` | `tutor_schedules` | 老師可授課時段驗證 |
| `Order` | `orders` | 新建訂單 |
| `Bookings` | `bookings` | 新建預約紀錄 |
| `WalletLog` | `wallet_logs` | 扣款交易紀錄 |
