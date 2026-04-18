# Kumo — OTA Update System (Backend)

> 雲端 OTA 韌體更新平台後端服務，提供裝置連線管理、Campaign 派送、即時狀態回報與報表功能。

---

## 目錄

- [專案概述](#專案概述)
- [系統架構圖](#系統架構圖)
- [線稿圖](#線稿圖)
- [ER Model 圖](#er-model-圖)
- [WebSocket 連線流程](#websocket-連線流程)
- [資料夾架構](#資料夾架構)
- [技術版本](#技術版本)
- [相關前端專案](#相關前端專案)
- [安裝與啟動](#安裝與啟動)
- [Swagger API 文件](#swagger-api-文件)
- [核心技術說明](#核心技術說明)

---

## 專案概述

Kumo 是一套 OTA（Over-The-Air）韌體更新管理平台，整個系統由三個 Repo 組成：

| Repo | 說明 |
|------|------|
| `kumo_backend` (本 Repo) | Spring Boot 後端，提供 REST API 與 WebSocket 服務 |
| `kumo_frontend` | 管理後台前端，供管理員操作 Campaign、查看裝置狀態與報表 |
| `kumo_demo_device` | 裝置端前端模擬器，模擬 IoT 裝置透過 WebSocket 回報更新狀態 |
| `kumo_deploy` | 使用 docker compose 快速在本地端部署 |

**核心功能：**
- 裝置連線與韌體版本回報（WebSocket / STOMP）
- OTA Campaign 建立、管理與派送
- 裝置連線紀錄查詢
- 各品牌裝置 7 日接收數報表
- JWT 身份驗證保護 REST API
- Redis 快取每日各品牌裝置接收數

---

## 系統架構圖

![截圖 2026-04-18 18.35.42.png](docs/%E6%88%AA%E5%9C%96%202026-04-18%2018.35.42.png)

---

## 線稿圖

> https://miro.com/app/board/uXjVJ-xg_CM=/

---

## ER Model 圖

![ER Model](docs/%E6%88%AA%E5%9C%96%202026-04-18%2018.30.56.png)


**資料表說明：**

| 資料表 | 說明 |
|--------|------|
| `devices` | 裝置基本資訊（SN、品牌、型號） |
| `connect_logs` | 裝置每次連線紀錄與狀態 |
| `campaigns` | OTA 更新任務設定 |
| `status_ref` | 裝置狀態參照表（received / succeeded / failed ...） |
| `download_by_ref` | 下載方式參照表 |

---

## WebSocket 連線流程

>https://mermaid.ai/app/projects/dd4a5778-54e5-4f18-a629-403442f9b1fb/diagrams/5cbbb6cb-1b69-4bea-83c8-c8bda499f62a/version/v0.1/edit

---

## 資料夾架構

```
kumo/
├── src/
│   ├── main/
│   │   ├── java/tw/idv/rainbow/
│   │   │   ├── KumoApplication.java          # 程式進入點
│   │   │   ├── common/
│   │   │   │   ├── ApiResult.java            # 統一 API 回應格式
│   │   │   │   └── JsonConverter.java        # JSON 轉換工具
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java       # Spring Security 設定
│   │   │   │   ├── SwaggerConfig.java        # OpenAPI / Swagger 設定
│   │   │   │   ├── WebSocketConfig.java      # STOMP WebSocket 設定
│   │   │   │   └── SpringMvcConfig.java      # MVC 設定
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java              # JWT 產生與驗證
│   │   │   │   └── JwtFilter.java            # JWT 請求過濾器
│   │   │   ├── web/
│   │   │   │   ├── controller/               # REST API Controllers
│   │   │   │   │   ├── LoginController.java
│   │   │   │   │   ├── DeviceController.java
│   │   │   │   │   ├── CampaignController.java
│   │   │   │   │   ├── ReportController.java
│   │   │   │   │   └── FileController.java
│   │   │   │   ├── dto/                      # 資料傳輸物件
│   │   │   │   ├── entity/                   # JPA 實體
│   │   │   │   ├── repository/               # Spring Data JPA Repositories
│   │   │   │   └── service/                  # 業務邏輯層
│   │   │   │       └── impl/
│   │   │   └── websocket/
│   │   │       └── ConnectController.java    # WebSocket 訊息處理
│   │   └── resources/
│   │       └── application.properties        # 應用程式設定
│   └── test/
├── uploads/                                  # 上傳韌體檔案存放目錄
├── pom.xml
└── README.md
```

---

## 技術版本

| 技術 | 版本 |
|------|------|
| Java | 17 |
| Spring Boot | 4.0.5 |
| Spring Security | (managed by Spring Boot) |
| Spring Data JPA | (managed by Spring Boot) |
| Spring WebSocket | (managed by Spring Boot) |
| Spring Data Redis (Lettuce) | (managed by Spring Boot) |
| MySQL Connector/J | (managed by Spring Boot) |
| JJWT | 0.11.5 |
| SpringDoc OpenAPI | 2.5.0 |
| JNanoID | 2.0.0 |
| Lombok | (managed by Spring Boot) |
| Maven | 4.0.0 |

---

## 其他相關專案

| 名稱 | Repo | 說明 |
|--|------|------|
| `kumo_frontend` | https://github.com/169628/kumo_frontend | 管理員操作介面，Campaign CRUD、裝置清單、報表儀表板 |
| `kumo_demo_device`  | https://github.com/169628/kumo_demo_device | 模擬 IoT 裝置，透過 WebSocket 連線並回報更新狀態 |
| `kumo_deploy`  | https://github.com/169628/kumo_deploy| 使用 docker compose 快速在本地端部署 |

---

## 安裝與啟動

> 請前往 https://github.com/169628/kumo_deploy

---

## Swagger API 文件

服務啟動後，可透過瀏覽器開啟 API 文件：

```
http://localhost:8080/kumo/api/swagger-ui.html
```

OpenAPI JSON Schema：

```
http://localhost:8080/kumo/api/v3/api-docs
```

> 大部分 API 需要 JWT 驗證，請先呼叫 `/login` 取得 Token，並在 Swagger UI 右上角點選 **Authorize**，輸入 `Bearer <token>`。

---

## API 一覽

| Method | Path | 說明 | 需要 JWT |
|--------|------|------|----------|
| POST | `/login` | 登入取得 JWT Token | 否 |
| GET | `/device` | 取得所有裝置最新狀態清單 | 是 |
| GET | `/device/{id}` | 取得指定裝置的連線紀錄 | 是 |
| GET | `/campaign` | 取得所有 Campaign 清單 | 是 |
| GET | `/campaign/{id}` | 取得指定 Campaign | 是 |
| POST | `/campaign` | 建立 Campaign（含韌體上傳） | 是 |
| PUT | `/campaign/{id}` | 更新 Campaign | 是 |
| DELETE | `/campaign/{id}` | 刪除 Campaign | 是 |
| PUT | `/campaign/enable/{id}` | 切換 Campaign 啟用狀態 | 是 |
| GET | `/report/received` | 各品牌裝置 7 日接收數報表 | 是 |
| GET | `/file/{fileName}` | 下載韌體檔案 | 否 |
| WS | `/endpoint` | WebSocket 連線端點（STOMP） | 否 |

---

## 核心技術說明

### JWT（JSON Web Token）

- 採用 **HMAC SHA-256（HS256）** 演算法簽署
- Token 有效期：**24 小時**
- 客戶端需在每次 REST API 請求的 Header 帶入：
  ```
  Authorization: Bearer <token>
  ```
- 以下路徑為公開端點，**不需要** JWT：
  - `POST /login`
  - `GET /file/*`
  - `WS /endpoint/**`
  - `/swagger-ui/**`、`/v3/api-docs/**`

---

### WebSocket（STOMP over SockJS）

- 使用 **STOMP** 協定，支援 **SockJS** 降級回傳
- 裝置端連線至 `/endpoint`，訂閱 `/msg/{sn}` 接收回應
- 裝置端傳送訊息至 `/connect/device/{sn}`
- 管理後台可訂閱 `/msg/{sn}` 即時接收裝置狀態更新
- WebSocket 連線**不需要** JWT 驗證

---

### Redis

- 使用 **Lettuce** 連線池
- 用途：快取每日各品牌已接收裝置的 SN Set
- Key 格式：`{brand}:{yyyy-MM-dd}`
- TTL：**30 天**
- 供報表 API 計算近 7 日各品牌接收數

---

### 預設帳號

| 帳號 | 密碼 |
|------|------|
| `admin` | `admin` |
