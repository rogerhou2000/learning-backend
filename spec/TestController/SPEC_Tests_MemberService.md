# 測試規格文件 - MemberServiceTest

來源檔案: `src/test/java/com/learning/api/service/MemberServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `MemberService.register()` 方法，驗證使用者建立、email 正規化、密碼雜湊及重複 email 防護。

---

## 測試方法一覽

**總計：4 個測試方法**

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `register_validRequest_savesUser` | 合法的新使用者資料 | 使用者儲存至資料庫，name 與 role 正確 |
| `register_duplicateEmail_throwsIllegalArgumentException` | 使用已存在的 email 再次註冊 | 拋出 `IllegalArgumentException` |
| `register_emailNormalized_savesAsLowercase` | email 含大寫字母（例如 `UPPER@Example.COM`） | 儲存為全小寫（`upper@example.com`） |
| `register_passwordIsHashed` | 純文字密碼 `password123` | 儲存的密碼不等於原始字串，且以 BCrypt prefix `$2` 開頭 |
