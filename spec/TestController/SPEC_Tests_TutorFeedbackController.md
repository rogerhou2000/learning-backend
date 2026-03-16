# 測試規格文件 - TutorFeedbackControllerTest

來源檔案: `src/test/java/com/learning/api/controller/TutorFeedbackControllerTest.java`

測試類型: 整合測試（`@SpringBootTest` + MockMvc）

---

## 概述

測試 `TutorFeedbackController` 的 `/api/teacher/feedbacks` 端點，驗證老師送出課後回饋功能。

---

## 前置設定（@BeforeEach）

建立以下測試資料：
- 老師使用者（role=2）
- 學生使用者（role=1）
- 預約紀錄（status=2，昨天的日期）

---

## 測試方法一覽

**總計：4 個測試方法**

### POST /api/teacher/feedbacks（4 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `submitFeedback_validRequest_shouldReturn200` | 正確的回饋資料（rating=4） | `200 OK` | `{ "message": "課後回饋送出成功！家長將會收到通知。" }` |
| `submitFeedback_ratingZero_shouldReturn400` | rating=0（低於最小值 1） | `400 Bad Request` | `{ "message": "評分必須介於 1 到 5 之間" }` |
| `submitFeedback_ratingSix_shouldReturn400` | rating=6（高於最大值 5） | `400 Bad Request` | `{ "message": "評分必須介於 1 到 5 之間" }` |
| `submitFeedback_duplicate_shouldReturn400` | 同一預約已送出過回饋 | `400 Bad Request` | `{ "message": "這堂課已經填寫過回饋囉！" }` |
