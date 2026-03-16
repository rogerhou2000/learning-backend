# 測試規格文件 - AuthServiceTest

來源檔案: `src/test/java/com/learning/api/service/AuthServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `AuthService.loginReq()` 方法，驗證登入驗證邏輯、JWT 回傳及錯誤處理。

---

## 前置設定（@BeforeEach）

- 建立測試使用者（email: `authsvc@example.com`，密碼已 BCrypt 加密）。

---

## 測試方法一覽

**總計：3 個測試方法**

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `loginReq_validCredentials_returnsToken` | 正確 email 與密碼 | 回傳 `LoginResp`，`token` 不為空 |
| `loginReq_wrongPassword_throwsIllegalArgumentException` | 正確 email 但密碼錯誤 | 拋出 `IllegalArgumentException` |
| `loginReq_nonExistentUser_throwsIllegalArgumentException` | 不存在的 email | 拋出 `IllegalArgumentException` |
