**Baas接口文档**


**简介**：<p>支持井通、墨客（公链及子链）的baas接口</p>

**HOST**:localhost:8080

**联系人**:望天科技

**Version**:1.0

**接口路径**：/v2/api-docs?group=v1.0

**配置**：

该项目属于java项目，使用springboot+redis+mysql+maven<br>

1.配置java环境<br>

2.安装redis<br>

3.安装mysql<br>

4.检出项目到myeclipse,eclipse,idea工具中，配置application.yml内的redis和mysql信息<br>

5.启动Application.java即可<br>

***接口访问测试：http://localhost:8080/doc.html<br>***


# 1.应用接口

## 1.APP注册


**接口描述**:


**接口地址**:`/api/app/register`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"appName": "",
	"introduce": "",
	"userType": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|registerApp| 注册信息  | body | true |RegisterApp  | RegisterApp   |

**schema属性说明**



**RegisterApp**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|appName| 应用名称  | body | true |string  |    |
|introduce| 应用介绍  | body | true |string  |    |
|userType| 用户类型，0：个人，1：企业  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"appId": "",
		"appSecret": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseApp  | ResponseApp   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseApp**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|appId | appId   |string  |    |
|appSecret | appSecret   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseApp»|
## 2.实名认证


**接口描述**:


**接口地址**:`/api/app/verify`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"appId": "",
	"idCard": "",
	"userName": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|verifyApp| 认证APP  | body | true |VerifyApp  | VerifyApp   |

**schema属性说明**



**VerifyApp**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|appId| appId  | body | true |string  |    |
|idCard| 身份证  | body | true |string  |    |
|userName| 姓名  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 3.获取accessToken(两小时失效请及时刷新)


**接口描述**:


**接口地址**:`/api/app/token`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|appId| appId  | query | true |string  |    |
|appSecret| appSecret  | query | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,object»»|
## 4.新增app墨客代币（app下所有用户都会显示该币）


**接口描述**:


**接口地址**:`/api/app/symbol/moac/add`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"chain": "",
	"symbol": "",
	"tokenAddress": "",
	"tokenType": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|appTokenAdd| 新增代币  | body | true |TokenAdd  | TokenAdd   |
|sign| 签名（将私钥与非空参数，按字典排序后字符串，如：a=1&b=2&c=3&appSecret=666,进行MD5加密）  | query | true |string  |    |

**schema属性说明**



**TokenAdd**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|chain| 区块链类型，0：墨客，1：井通  | body | true |string  |    |
|symbol| 代币名  | body | true |string  |    |
|tokenAddress| 代币地址，墨客为合约地址，井通为issuer地址  | body | true |string  |    |
|tokenType| token类型（墨客代币有效，0：ERC20,1:ERC721）  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"chain": "",
		"id": "",
		"symbol": "",
		"tokenAddress": "",
		"tokenType": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseToken  | ResponseToken   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseToken**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 代币id   |string  |    |
|symbol | 代币名   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|tokenType | token类型（墨客代币有效，0：ERC20,1:ERC721）   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseToken»|
## 5.删除app代币


**接口描述**:


**接口地址**:`/api/app/symbol/{id}`


**请求方式**：`DELETE`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|id| id  | path | true |string  |    |
|sign| 签名（将私钥与非空参数，按字典排序后字符串，如：a=1&b=2&c=3&appSecret=666,进行MD5加密）  | query | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 6.app代币列表


**接口描述**:


**接口地址**:`/api/app/symbol/list`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"appId": "",
			"chain": "",
			"createDate": "",
			"createId": "",
			"id": "",
			"isDelete": "",
			"lastUpdateId": "",
			"lastUpdateTime": "",
			"mark": "",
			"symbol": "",
			"tokenAddress": "",
			"tokenType": ""
		}
	],
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |array  | AppToken   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**AppToken**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|appId | appId   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|symbol | 代币名   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|tokenType | token类型（墨客代币有效，0：ERC20,1:ERC721）   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«List«AppToken»»|
# 2.钱包接口

## 1.创建钱包


**接口描述**:


**接口地址**:`/api/wallet/v1/create`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"payPsw": "",
	"walletName": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|registerWallet| 注册钱包  | body | true |RegisterWallet  | RegisterWallet   |

**schema属性说明**



**RegisterWallet**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|payPsw| 支付密码  | body | true |string  |    |
|walletName| 钱包名称  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,object»»|
## 2.认证钱包


**接口描述**:


**接口地址**:`/api/wallet/v1/verify`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"idCard": "",
	"userName": "",
	"walletId": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|verifyWallet| 认证钱包  | body | true |VerifyWallet  | VerifyWallet   |

**schema属性说明**



**VerifyWallet**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|idCard| 用户身份证  | body | true |string  |    |
|userName| 用户姓名  | body | true |string  |    |
|walletId| 钱包id  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 3.钱包绑定邮箱


**接口描述**:


**接口地址**:`/api/wallet/v1/bindEmail`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"email": "",
	"payPsw": "",
	"walletId": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|bindEmail| 绑定邮箱  | body | true |BindEmail  | BindEmail   |

**schema属性说明**



**BindEmail**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|email| 邮箱  | body | true |string  |    |
|payPsw| 支付密码  | body | true |string  |    |
|walletId| 钱包id  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 4.修改支付密码


**接口描述**:


**接口地址**:`/api/wallet/v1/updatePayPsw`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"newPayPsw": "",
	"oldPayPsw": "",
	"walletId": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|updatePayPsw| 修改支付密码  | body | true |UpdatePayPsw  | UpdatePayPsw   |

**schema属性说明**



**UpdatePayPsw**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|newPayPsw| 新支付密码  | body | true |string  |    |
|oldPayPsw| 原支付密码  | body | true |string  |    |
|walletId| 钱包id  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 5.重置支付密码（邮件）


**接口描述**:


**接口地址**:`/api/wallet/v1/findPayPsw`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| 钱包id  | query | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 6.新增app墨客代币（用户代币）


**接口描述**:


**接口地址**:`/api/wallet/v1/symbol/moac/add`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"chain": "",
	"symbol": "",
	"tokenAddress": "",
	"tokenType": ""
}
```


**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|appTokenAdd| 新增代币  | body | true |TokenAdd  | TokenAdd   |
|walletId| walletId  | header | true |string  |    |

**schema属性说明**



**TokenAdd**

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|chain| 区块链类型，0：墨客，1：井通  | body | true |string  |    |
|symbol| 代币名  | body | true |string  |    |
|tokenAddress| 代币地址，墨客为合约地址，井通为issuer地址  | body | true |string  |    |
|tokenType| token类型（墨客代币有效，0：ERC20,1:ERC721）  | body | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"chain": "",
		"id": "",
		"symbol": "",
		"tokenAddress": "",
		"tokenType": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseToken  | ResponseToken   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseToken**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 代币id   |string  |    |
|symbol | 代币名   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|tokenType | token类型（墨客代币有效，0：ERC20,1:ERC721）   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseToken»|
## 7.删除代币


**接口描述**:


**接口地址**:`/api/wallet/v1/symbol/{id}`


**请求方式**：`DELETE`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|id| id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 8.代币列表(app代币--id为'system')


**接口描述**:


**接口地址**:`/api/wallet/v1/symbol/list`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"appId": "",
			"chain": "",
			"createDate": "",
			"createId": "",
			"id": "",
			"isDelete": "",
			"lastUpdateId": "",
			"lastUpdateTime": "",
			"mark": "",
			"symbol": "",
			"tokenAddress": "",
			"tokenType": "",
			"walletId": ""
		}
	],
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |array  | UserToken   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**UserToken**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|appId | appId   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|symbol | 代币名   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|tokenType | token类型（墨客代币有效，0：ERC20,1:ERC721）   |string  |    |
|walletId | 钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«List«UserToken»»|
## 9.获取钱包余额


**接口描述**:


**接口地址**:`/api/wallet/v1/balance`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
[
	{
		"address": "",
		"list": [
			{
				"chain": "",
				"symbol": "",
				"tokenAddress": "",
				"tokenType": "",
				"value": ""
			}
		]
	}
]
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|address| 账户地址  |string  |    |
|list| 余额信息  |array  | ResponseBalanceItem   |



**schema属性说明**




**ResponseBalanceItem**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|symbol | 代币名   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|tokenType | token类型（墨客代币有效，0：ERC20,1:ERC721）   |string  |    |
|value | 余额   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBalance|
# 3.账户接口

## 1.创建墨客账户


**接口描述**:


**接口地址**:`/api/account/moac/create`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"address": "",
		"chain": "",
		"id": "",
		"privateKey": "",
		"publicKey": "",
		"walletId": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseAccount  | ResponseAccount   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseAccount**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|address | 账户地址   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 账户id   |string  |    |
|privateKey | 私钥   |string  |    |
|publicKey | 公钥   |string  |    |
|walletId | 钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseAccount»|
## 2.导入墨客账户


**接口描述**:


**接口地址**:`/api/account/moac/import`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|privateKey| 私钥  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"address": "",
		"chain": "",
		"id": "",
		"privateKey": "",
		"publicKey": "",
		"walletId": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseAccount  | ResponseAccount   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseAccount**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|address | 账户地址   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 账户id   |string  |    |
|privateKey | 私钥   |string  |    |
|publicKey | 公钥   |string  |    |
|walletId | 钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseAccount»|
## 3.创建井通账户（需转入20SWT激活）


**接口描述**:


**接口地址**:`/api/account/jingtum/create`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"address": "",
		"chain": "",
		"id": "",
		"privateKey": "",
		"publicKey": "",
		"walletId": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseAccount  | ResponseAccount   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseAccount**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|address | 账户地址   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 账户id   |string  |    |
|privateKey | 私钥   |string  |    |
|publicKey | 公钥   |string  |    |
|walletId | 钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseAccount»|
## 4.导入井通账户


**接口描述**:


**接口地址**:`/api/account/jingtum/import`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|privateKey| 私钥  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"address": "",
		"chain": "",
		"id": "",
		"privateKey": "",
		"publicKey": "",
		"walletId": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseAccount  | ResponseAccount   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseAccount**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|address | 账户地址   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 账户id   |string  |    |
|privateKey | 私钥   |string  |    |
|publicKey | 公钥   |string  |    |
|walletId | 钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseAccount»|
## 5.删除账户


**接口描述**:


**接口地址**:`/api/account/{id}`


**请求方式**：`DELETE`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|id| 账户id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 6.账户列表（不再返回私钥）


**接口描述**:


**接口地址**:`/api/account/list`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"address": "",
			"chain": "",
			"id": "",
			"privateKey": "",
			"publicKey": "",
			"walletId": ""
		}
	],
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |array  | ResponseAccount   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseAccount**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|address | 账户地址   |string  |    |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|id | 账户id   |string  |    |
|privateKey | 私钥   |string  |    |
|publicKey | 公钥   |string  |    |
|walletId | 钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«List«ResponseAccount»»|
## 7.获取账户余额


**接口描述**:


**接口地址**:`/api/account/v1/balance/{id}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|id| 账户id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"address": "",
	"list": [
		{
			"chain": "",
			"symbol": "",
			"tokenAddress": "",
			"tokenType": "",
			"value": ""
		}
	]
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|address| 账户地址  |string  |    |
|list| 余额信息  |array  | ResponseBalanceItem   |



**schema属性说明**




**ResponseBalanceItem**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|symbol | 代币名   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|tokenType | token类型（墨客代币有效，0：ERC20,1:ERC721）   |string  |    |
|value | 余额   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBalance|
## 8.导出账户信息


**接口描述**:


**接口地址**:`/api/account/v1/export/{id}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|id| 账户id  | path | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"account": "",
		"amount": {
			"currency": "",
			"issuer": "",
			"value": ""
		},
		"date": 0,
		"destination": "",
		"fee": "",
		"flags": 0,
		"hash": "",
		"inLedger": 0,
		"ledgerIndex": "",
		"memos": [
			{
				"memoData": "",
				"memoType": ""
			}
		],
		"meta": {
			"affectedNodes": [
				{
					"modifiedNode": {
						"finalFields": {
							"account": "",
							"balance": "",
							"flags": 0,
							"ownerCount": 0,
							"sequence": 0
						},
						"ledgerEntryType": "",
						"ledgerIndex": "",
						"previousFields": {
							"balance": ""
						},
						"previousTxnID": "",
						"previousTxnLgrSeq": 0
					}
				}
			],
			"transactionIndex": 0,
			"transactionResult": ""
		},
		"sequence": 0,
		"signingPubKey": "",
		"timestamp": 0,
		"transactionType": "",
		"txnSignature": "",
		"validated": true
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |Account  | Account   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**Account**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account |    |string  |    |
|amount |    |AmountInfo  | AmountInfo   |
|date |    |integer(int32)  |    |
|destination |    |string  |    |
|fee |    |string  |    |
|flags |    |integer(int64)  |    |
|hash |    |string  |    |
|inLedger |    |integer(int32)  |    |
|ledgerIndex |    |string  |    |
|memos |    |array  | Memo   |
|meta |    |Meta  | Meta   |
|sequence |    |integer(int32)  |    |
|signingPubKey |    |string  |    |
|timestamp |    |integer(int32)  |    |
|transactionType |    |string  |    |
|txnSignature |    |string  |    |
|validated |    |boolean  |    |

**AmountInfo**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|currency |    |string  |    |
|issuer |    |string  |    |
|value |    |string  |    |

**Memo**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|memoData |    |string  |    |
|memoType |    |string  |    |

**Meta**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|affectedNodes |    |array  | AffectedNode   |
|transactionIndex |    |integer(int32)  |    |
|transactionResult |    |string  |    |

**AffectedNode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|modifiedNode |    |ModifiedNode  | ModifiedNode   |

**ModifiedNode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|finalFields |    |FinalFields  | FinalFields   |
|ledgerEntryType |    |string  |    |
|ledgerIndex |    |string  |    |
|previousFields |    |PreviousFields  | PreviousFields   |
|previousTxnID |    |string  |    |
|previousTxnLgrSeq |    |integer(int32)  |    |

**FinalFields**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account |    |string  |    |
|balance |    |string  |    |
|flags |    |integer(int32)  |    |
|ownerCount |    |integer(int32)  |    |
|sequence |    |integer(int32)  |    |

**PreviousFields**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|balance |    |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Account»|
# 4.墨客接口

## 1.墨客账户获取nonce，通过递增nonce多次转账可实现批量转账


**接口描述**:


**接口地址**:`/api/moac/v1/nonce`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 2.墨客原生币转账/文本上链


**接口描述**:


**接口地址**:`/api/moac/v1/transfer`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|data| 文本内容（备注）  | query | false |string  |    |
|gas| 手续费（可不传，默认传最大值9E-12）  | query | false |string  |    |
|nonce| 账户账户nonce(可不传，默认为账户当前nonce)  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|to| 接收方账户地址  | query | true |string  |    |
|value| 转账数量(文本上链则传0)  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 3.墨客获取交易信息


**接口描述**:


**接口地址**:`/api/moac/v1/transaction`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|hash| 交易hash  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"actualGasCost": "",
		"blockHash": "",
		"blockNumber": "",
		"contractAddress": "",
		"creates": "",
		"from": "",
		"gas": "",
		"gasPrice": "",
		"gasUsed": "",
		"hash": "",
		"input": "",
		"logs": [
			{
				"address": "",
				"blockHash": "",
				"blockNumber": 0,
				"blockNumberRaw": "",
				"data": "",
				"logIndex": 0,
				"logIndexRaw": "",
				"removed": true,
				"topics": [],
				"transactionHash": "",
				"transactionIndex": 0,
				"transactionIndexRaw": "",
				"type": ""
			}
		],
		"logsBloom": "",
		"nonce": "",
		"publicKey": "",
		"r": "",
		"raw": "",
		"root": "",
		"s": "",
		"status": true,
		"to": "",
		"transactionIndex": "",
		"v": 0,
		"value": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseTransactionMoac  | ResponseTransactionMoac   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseTransactionMoac**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|actualGasCost | gas实际花费   |string  |    |
|blockHash | 区块hash   |string  |    |
|blockNumber | 区块号   |string  |    |
|contractAddress | 对方账户或合约地址   |string  |    |
|creates |    |string  |    |
|from | 发起者   |string  |    |
|gas | gas使用数量   |string  |    |
|gasPrice | gas价格   |string  |    |
|gasUsed | gas实际使用数量   |string  |    |
|hash | 交易hash   |string  |    |
|input | 交易备注   |string  |    |
|logs |    |array  | Log   |
|logsBloom |    |string  |    |
|nonce | 账户nonce   |string  |    |
|publicKey |    |string  |    |
|r |    |string  |    |
|raw |    |string  |    |
|root |    |string  |    |
|s |    |string  |    |
|status | 交易状态   |boolean  |    |
|to | 接收者   |string  |    |
|transactionIndex | 区块中该交易序号   |string  |    |
|v |    |integer(int64)  |    |
|value | 转账金额   |string  |    |

**Log**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|address |    |string  |    |
|blockHash |    |string  |    |
|blockNumber |    |integer  |    |
|blockNumberRaw |    |string  |    |
|data |    |string  |    |
|logIndex |    |integer  |    |
|logIndexRaw |    |string  |    |
|removed |    |boolean  |    |
|topics |    |array  |    |
|transactionHash |    |string  |    |
|transactionIndex |    |integer  |    |
|transactionIndexRaw |    |string  |    |
|type |    |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseTransactionMoac»|
## 4.查询当前区块号


**接口描述**:


**接口地址**:`/api/moac/v1/blockNumber`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
# 5.井通接口

## 1.井通原生币及其他代币转账/文本上链


**接口描述**:


**接口地址**:`/api/jintum/v1/transfer`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 井通账户id  | query | true |string  |    |
|currency| 代币名，默认为'SWT'  | query | true |string  |    |
|data| 文本内容（备注）  | query | false |string  |    |
|issuer| 银关地址，'SWT'转账可为空  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|to| 接收方账户地址  | query | true |string  |    |
|value| 转账数量(文本上链也需大于0)  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"account": "",
		"amount": "",
		"destination": "",
		"fee": "",
		"flags": 0,
		"hash": "",
		"limitAmount": {
			"account": "",
			"amount": {
				"currency": "",
				"issuer": "",
				"value": ""
			},
			"date": 0,
			"destination": "",
			"fee": "",
			"flags": 0,
			"hash": "",
			"inLedger": 0,
			"ledgerIndex": "",
			"memos": [
				{
					"memoData": "",
					"memoType": ""
				}
			],
			"meta": {
				"affectedNodes": [
					{
						"modifiedNode": {
							"finalFields": {
								"account": "",
								"balance": "",
								"flags": 0,
								"ownerCount": 0,
								"sequence": 0
							},
							"ledgerEntryType": "",
							"ledgerIndex": "",
							"previousFields": {
								"balance": ""
							},
							"previousTxnID": "",
							"previousTxnLgrSeq": 0
						}
					}
				],
				"transactionIndex": 0,
				"transactionResult": ""
			},
			"sequence": 0,
			"signingPubKey": "",
			"timestamp": 0,
			"transactionType": "",
			"txnSignature": "",
			"validated": true
		},
		"memos": [],
		"offerSequence": 0,
		"relationType": 0,
		"sequence": 0,
		"signingPubKey": "",
		"takerGets": {
			"currency": "",
			"issuer": "",
			"value": ""
		},
		"takerpays": "",
		"target": "",
		"timestamp": 0,
		"transactionType": "",
		"txnSignature": ""
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseTxJson  | ResponseTxJson   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseTxJson**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account | 账号地址   |string  |    |
|amount | 账号地址   |string  |    |
|destination | 对家   |string  |    |
|fee | 交易费   |string  |    |
|flags |    |integer(int64)  |    |
|hash | 交易hash   |string  |    |
|limitAmount | 关系的额度   |Account  | Account   |
|memos | 交易标记   |array  |    |
|offerSequence | 取消的单子号   |integer(int32)  |    |
|relationType | 关系类型：0信任；1授权；3冻结/解冻   |integer(int32)  |    |
|sequence | 单子序列号   |integer(int32)  |    |
|signingPubKey | 签名公钥   |string  |    |
|takerGets | 对家得到的Object   |AmountInfo  | AmountInfo   |
|takerpays | 对家支付的Object   |string  |    |
|target | 关系对家   |string  |    |
|timestamp | 时间戳   |integer(int32)  |    |
|transactionType | 交易类型   |string  |    |
|txnSignature | 交易签名   |string  |    |

**Account**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account |    |string  |    |
|amount |    |AmountInfo  | AmountInfo   |
|date |    |integer(int32)  |    |
|destination |    |string  |    |
|fee |    |string  |    |
|flags |    |integer(int64)  |    |
|hash |    |string  |    |
|inLedger |    |integer(int32)  |    |
|ledgerIndex |    |string  |    |
|memos |    |array  | Memo   |
|meta |    |Meta  | Meta   |
|sequence |    |integer(int32)  |    |
|signingPubKey |    |string  |    |
|timestamp |    |integer(int32)  |    |
|transactionType |    |string  |    |
|txnSignature |    |string  |    |
|validated |    |boolean  |    |

**AmountInfo**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|currency |    |string  |    |
|issuer |    |string  |    |
|value |    |string  |    |

**Memo**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|memoData |    |string  |    |
|memoType |    |string  |    |

**Meta**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|affectedNodes |    |array  | AffectedNode   |
|transactionIndex |    |integer(int32)  |    |
|transactionResult |    |string  |    |

**AffectedNode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|modifiedNode |    |ModifiedNode  | ModifiedNode   |

**ModifiedNode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|finalFields |    |FinalFields  | FinalFields   |
|ledgerEntryType |    |string  |    |
|ledgerIndex |    |string  |    |
|previousFields |    |PreviousFields  | PreviousFields   |
|previousTxnID |    |string  |    |
|previousTxnLgrSeq |    |integer(int32)  |    |

**FinalFields**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account |    |string  |    |
|balance |    |string  |    |
|flags |    |integer(int32)  |    |
|ownerCount |    |integer(int32)  |    |
|sequence |    |integer(int32)  |    |

**PreviousFields**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|balance |    |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseTxJson»|
## 2.井通获取交易信息


**接口描述**:


**接口地址**:`/api/jintum/v1/transaction`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|hash| 交易hash  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"account": "",
		"amount": {
			"currency": "",
			"issuer": "",
			"value": ""
		},
		"date": 0,
		"destination": "",
		"fee": "",
		"flags": 0,
		"hash": "",
		"inLedger": 0,
		"ledgerIndex": "",
		"memos": [
			{
				"memoData": "",
				"memoType": ""
			}
		],
		"meta": {
			"affectedNodes": [
				{
					"modifiedNode": {
						"finalFields": {
							"account": "",
							"balance": "",
							"flags": 0,
							"ownerCount": 0,
							"sequence": 0
						},
						"ledgerEntryType": "",
						"ledgerIndex": "",
						"previousFields": {
							"balance": ""
						},
						"previousTxnID": "",
						"previousTxnLgrSeq": 0
					}
				}
			],
			"transactionIndex": 0,
			"transactionResult": ""
		},
		"sequence": 0,
		"signingPubKey": "",
		"timestamp": 0,
		"transactionType": "",
		"txnSignature": "",
		"validated": true
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseTransactionJingTum  | ResponseTransactionJingTum   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseTransactionJingTum**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account | 钱包地址   |string  |    |
|amount | 交易金额   |AmountInfo  | AmountInfo   |
|date | 交易进账本时间   |integer(int32)  |    |
|destination | 交易对家地址   |string  |    |
|fee | 交易费   |string  |    |
|flags | 交易标记   |integer(int64)  |    |
|hash | 交易hash   |string  |    |
|inLedger | 交易所在的账本号   |integer(int32)  |    |
|ledgerIndex | 账本高度   |string  |    |
|memos | 备注   |array  | Memo   |
|meta | 交易影响的节点   |Meta  | Meta   |
|sequence | 自身账号的交易号   |integer(int32)  |    |
|signingPubKey | 签名公钥   |string  |    |
|timestamp | 交易提交时间戳   |integer(int32)  |    |
|transactionType | 交易类型   |string  |    |
|txnSignature | 交易签名   |string  |    |
|validated | 交易是否通过验证   |boolean  |    |

**AmountInfo**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|currency |    |string  |    |
|issuer |    |string  |    |
|value |    |string  |    |

**Memo**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|memoData |    |string  |    |
|memoType |    |string  |    |

**Meta**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|affectedNodes |    |array  | AffectedNode   |
|transactionIndex |    |integer(int32)  |    |
|transactionResult |    |string  |    |

**AffectedNode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|modifiedNode |    |ModifiedNode  | ModifiedNode   |

**ModifiedNode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|finalFields |    |FinalFields  | FinalFields   |
|ledgerEntryType |    |string  |    |
|ledgerIndex |    |string  |    |
|previousFields |    |PreviousFields  | PreviousFields   |
|previousTxnID |    |string  |    |
|previousTxnLgrSeq |    |integer(int32)  |    |

**FinalFields**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|account |    |string  |    |
|balance |    |string  |    |
|flags |    |integer(int32)  |    |
|ownerCount |    |integer(int32)  |    |
|sequence |    |integer(int32)  |    |

**PreviousFields**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|balance |    |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseTransactionJingTum»|
# 6.交易记录接口

## 1.返回所有交易记录(注：该接口仅可查询由该钱包下账户所发起的交易)


**接口描述**:


**接口地址**:`/api/transaction/v1/list`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|page| 页  | query | true |string  |    |
|rows| 条  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"rows": [
			{
				"chain": "",
				"chainType": "",
				"createDate": "",
				"createId": "",
				"fromAddress": "",
				"hash": "",
				"id": "",
				"isDelete": "",
				"lastUpdateId": "",
				"lastUpdateTime": "",
				"mark": "",
				"symbol": "",
				"toAddress": "",
				"tokenAddress": "",
				"walletId": ""
			}
		],
		"total": 0
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |BoostrapTable«Transaction»  | BoostrapTable«Transaction»   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**BoostrapTable«Transaction»**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|rows | 分页记录   |array  | Transaction   |
|total | 总条数   |integer(int32)  |    |

**Transaction**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|chainType | 墨客区块链类型，0:公链，1：子链   |string  |    |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|fromAddress | 发起交易账户地址   |string  |    |
|hash | 交易hash   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|symbol | 代币名   |string  |    |
|toAddress | 接收账户地址   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|walletId | 发起账户钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«BoostrapTable«Transaction»»|
## 2.返回墨客交易记录(注：该接口可查询该账户作为发起方或接收方的平台内所有交易)


**接口描述**:


**接口地址**:`/api/transaction/v1/list/moac`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|address| 发起或收账户地址  | query | false |string  |    |
|page| 页  | query | true |string  |    |
|rows| 条  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"rows": [
			{
				"chain": "",
				"chainType": "",
				"createDate": "",
				"createId": "",
				"fromAddress": "",
				"hash": "",
				"id": "",
				"isDelete": "",
				"lastUpdateId": "",
				"lastUpdateTime": "",
				"mark": "",
				"symbol": "",
				"toAddress": "",
				"tokenAddress": "",
				"walletId": ""
			}
		],
		"total": 0
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |BoostrapTable«Transaction»  | BoostrapTable«Transaction»   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**BoostrapTable«Transaction»**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|rows | 分页记录   |array  | Transaction   |
|total | 总条数   |integer(int32)  |    |

**Transaction**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|chainType | 墨客区块链类型，0:公链，1：子链   |string  |    |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|fromAddress | 发起交易账户地址   |string  |    |
|hash | 交易hash   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|symbol | 代币名   |string  |    |
|toAddress | 接收账户地址   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|walletId | 发起账户钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«BoostrapTable«Transaction»»|
## 3.返回井通交易记录(注：该接口可查询该账户作为发起方或接收方的平台内所有交易)


**接口描述**:


**接口地址**:`/api/transaction/v1/list/jingtum`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|address| 发起或收账户地址  | query | false |string  |    |
|page| 页  | query | true |string  |    |
|rows| 条  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"rows": [
			{
				"chain": "",
				"chainType": "",
				"createDate": "",
				"createId": "",
				"fromAddress": "",
				"hash": "",
				"id": "",
				"isDelete": "",
				"lastUpdateId": "",
				"lastUpdateTime": "",
				"mark": "",
				"symbol": "",
				"toAddress": "",
				"tokenAddress": "",
				"walletId": ""
			}
		],
		"total": 0
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |BoostrapTable«Transaction»  | BoostrapTable«Transaction»   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**BoostrapTable«Transaction»**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|rows | 分页记录   |array  | Transaction   |
|total | 总条数   |integer(int32)  |    |

**Transaction**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|chain | 区块链类型，0：墨客，1：井通   |string  |    |
|chainType | 墨客区块链类型，0:公链，1：子链   |string  |    |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|fromAddress | 发起交易账户地址   |string  |    |
|hash | 交易hash   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|symbol | 代币名   |string  |    |
|toAddress | 接收账户地址   |string  |    |
|tokenAddress | 代币地址，墨客为合约地址，井通为issuer地址   |string  |    |
|walletId | 发起账户钱包id   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«BoostrapTable«Transaction»»|
# 7.墨客合约接口

## 1.部署合约

**接口描述**:返回部署结果

**接口地址**:`/api/moac/contract/v1/deploy`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|code| 合约编译代码（不包含0x）  | query | true |string  |    |
|params| 合约构造函数参数，实例如：[{"type":"bool","value":"false"},{"type":"string","value":"1212"},{"type":"address","value":"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60"},{"type":"uint8","value":"10"},{"type":"uint256","value":"10000"}]  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 2.获取合约地址

**接口描述**:返回合约地址

**接口地址**:`/api/moac/contract/v1/{contractHash}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractHash| 部署合约返回hash  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 3.调用非交易合约方法


**接口描述**:


**接口地址**:`/api/moac/contract/v1/call`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|functionName| 合约方法名  | query | true |string  |    |
|inParams| 合约方法入参，如：[{"type":"bool","value":"false"},{"type":"string","value":"1212"},{"type":"address","value":"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60"},{"type":"uint8","value":"10"},{"type":"uint256","value":"10000"}]  | query | false |string  |    |
|outParams| 合约出参，如：[{"type":"bool"}]  | query | false |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": [],
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |array  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«List«string»»|
## 4.调用交易类合约方法


**接口描述**:


**接口地址**:`/api/moac/contract/v1/callTransaction`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|functionName| 合约方法名  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|inParams| 合约方法入参，如：[{"type":"bool","value":"false"},{"type":"string","value":"1212"},{"type":"address","value":"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60"},{"type":"uint8","value":"10"},{"type":"uint256","value":"10000"}]  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|outParams| 合约出参，如：[{"type":"bool"}]  | query | false |string  |    |
|payPsw| 支付密码  | query | false |string  |    |
|value| 原生币转账数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 5.获取代币余额（支持erc20,erc721）


**接口描述**:


**接口地址**:`/api/moac/contract/v1/balance`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 6.获取代币总发行量（支持erc20,erc721）


**接口描述**:


**接口地址**:`/api/moac/contract/v1/totalSupply`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 7.获取合约名称（支持erc20,erc721）


**接口描述**:


**接口地址**:`/api/moac/contract/v1/name`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 8.获取代币token编号（支持erc20,erc721）


**接口描述**:


**接口地址**:`/api/moac/contract/v1/symbol`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 9.erc20获取代币decimals


**接口描述**:


**接口地址**:`/api/moac/contract/v1/decimals20`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 10.erc20代币转账


**接口描述**:


**接口地址**:`/api/moac/contract/v1/transfer20`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|to| 接受地址  | query | true |string  |    |
|tokenValue| token转账数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 11.erc20代币授权他人操作


**接口描述**:


**接口地址**:`/api/moac/contract/v1/approve20`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|spender| 被授权账户地址  | query | true |string  |    |
|tokenValue| 授权token数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 12.erc20获取授权代币数量


**接口描述**:


**接口地址**:`/api/moac/contract/v1/allowance20`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|spender| 被授权账户地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 13.erc20代替他人转账


**接口描述**:


**接口地址**:`/api/moac/contract/v1/transferFrom20`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|from| 授权账户地址  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|to| 转账接收者  | query | true |string  |    |
|tokenValue| 代理转账token数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 14.erc721代币所属者


**接口描述**:


**接口地址**:`/api/moac/contract/v1/ownerOf721`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|tokenId| 721tokenId  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 15.erc721代币是否存在


**接口描述**:


**接口地址**:`/api/moac/contract/v1/exists721`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|tokenId| 721tokenId  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 16.erc721转账或代理转账


**接口描述**:


**接口地址**:`/api/moac/contract/v1/transferFrom721`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|from| 授权地址  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|to| 转账接收地址  | query | true |string  |    |
|tokenId| 721tokenId  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 17.erc721授权给他人操作某个币


**接口描述**:


**接口地址**:`/api/moac/contract/v1/approve721`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|spender| 被授权账户地址  | query | true |string  |    |
|tokenId| 授权的712tokenId  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 18.erc721获取该代币所授权的人


**接口描述**:


**接口地址**:`/api/moac/contract/v1/getApproved721`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|tokenId| 712tokenId  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 19.erc721新增或取消全部授权


**接口描述**:


**接口地址**:`/api/moac/contract/v1/setApprovalForAll721`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|approved| 授权操作，0：授权   1：取消授权  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|gas| gasLimit，可不传  | query | false |string  |    |
|nonce| 账户nonce，单次交易可不传  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|spender| 被授权账户地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 20.erc721是否全部授权给他人


**接口描述**:


**接口地址**:`/api/moac/contract/v1/isApprovedForAll721`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|spender| 被授权账户地址  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 21.erc721获取代币URI信息


**接口描述**:


**接口地址**:`/api/moac/contract/v1/tokenURI721`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|tokenId| 721tokenId  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
# 8.墨客子链接口

## 1.部署子链


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/deploy`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id,确保MOAC余额充足  | query | true |string  |    |
|addFund| 子链初始化充入MOAC币数量,默认为1  | query | true |string  |    |
|erc20Code| erc20合约编码  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|scs| scs信息（默认会从管理账户给每个scs转入1个MOAC作为gas使用），如：[{"scsAddress":"b08253ff1f9315ea8a4ea02ab0d44272b6ce0caa"},{"scsAddress":"c9e6e1d3eb97a3b98d1a80b7bdeccbfc43ee82d8"}]  | query | true |string  |    |
|subchainBaseCode| 子链合约编码  | query | true |string  |    |
|subchainBaseErcRate| erc20代币与子链代币兑换比率,默认为1  | query | true |string  |    |
|subchainBaseFlushRound| 周期数，40~500之间  | query | true |string  |    |
|subchainBaseMax| 子链最多scs数，11、21、31、51、99选择  | query | true |string  |    |
|subchainBaseMin| 子链最少scs数，1、3、5、7选择  | query | true |string  |    |
|subchainBaseMonitorBond| 注册为monitor最少押金，合约中默认为1MOAC  | query | true |string  |    |
|subchainProtocolBaseCode| 子链协议合约编码  | query | true |string  |    |
|subchainProtocolBmin| scs注册最少押金,最少填1  | query | true |string  |    |
|subchainProtocolName| 子链协议合约名称  | query | true |string  |    |
|subchainProtocolType| 0表示pos，1表示ipfs  | query | true |string  |    |
|via| 子链收益账户地址  | query | true |string  |    |
|vnode| vnode信息列表,如：[{"vnodeUrl":"192.168.2.124:50062","vnodeAddress":"0x3b712f8cdb68353bf12b1e665cd86a5774816701"},{"vnodeUrl":"192.168.2.117:50062","vnodeAddress":"0x29c43903054cfc3bd36f5b57d24360e4c6e65342"}]  | query | true |string  |    |
|vnodeBmin| 注册vnode最少押金,默认为0  | query | true |string  |    |
|vnodeCode| vnode合约编码,为空则使用系统默认的  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 2.获取子链部署信息

**接口描述**:返回已完成信息

**接口地址**:`/api/moac/subchain/v1/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"scsList": [
			{
				"createDate": "",
				"createId": "",
				"id": "",
				"isDelete": "",
				"lastUpdateId": "",
				"lastUpdateTime": "",
				"mark": "",
				"monitorUrl": "",
				"scsAddress": "",
				"scsMonitor": "",
				"subchainId": ""
			}
		],
		"subchain": {
			"accountId": "",
			"createDate": "",
			"createId": "",
			"dappbaseAddress": "",
			"dappbaseCode": "",
			"dappbaseHash": "",
			"endBlock": "",
			"erc20Address": "",
			"erc20Code": "",
			"erc20Hash": "",
			"failReason": "",
			"id": "",
			"isDelete": "",
			"lastUpdateId": "",
			"lastUpdateTime": "",
			"mark": "",
			"subchainBaseAddress": "",
			"subchainBaseCode": "",
			"subchainBaseErcRate": "",
			"subchainBaseFlushRound": "",
			"subchainBaseHash": "",
			"subchainBaseMax": "",
			"subchainBaseMin": "",
			"subchainBaseMonitorBond": "",
			"subchainBaseThousandth": "",
			"subchainProtocolAddress": "",
			"subchainProtocolBaseCode": "",
			"subchainProtocolBmin": "",
			"subchainProtocolHash": "",
			"subchainProtocolName": "",
			"subchainProtocolType": "",
			"subchainStatus": "",
			"via": "",
			"vnodeAddress": "",
			"vnodeBmin": "",
			"vnodeCode": "",
			"vnodeHash": ""
		},
		"vnodeList": [
			{
				"createDate": "",
				"createId": "",
				"id": "",
				"isDelete": "",
				"lastUpdateId": "",
				"lastUpdateTime": "",
				"mark": "",
				"subchainId": "",
				"vnodeAddress": "",
				"vnodeUrl": ""
			}
		]
	},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |ResponseSubchainData  | ResponseSubchainData   |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |



**schema属性说明**




**ResponseSubchainData**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|scsList | 子链scs列表信息   |array  | Scs   |
|subchain | 子链基础信息   |Subchain  | Subchain   |
|vnodeList | 子链vnode列表信息   |array  | Vnode   |

**Scs**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|monitorUrl | monitor查询接口地址   |string  |    |
|scsAddress | scs地址   |string  |    |
|scsMonitor | 0:普通scs,1:monitor scs   |string  |    |
|subchainId | 子链id   |string  |    |

**Subchain**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|accountId | 管理账户id   |string  |    |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|dappbaseAddress | dappbase合约地址(子链合约地址)   |string  |    |
|dappbaseCode | dappbase合约编码   |string  |    |
|dappbaseHash | dappbase合约部署hash(子链hash)   |string  |    |
|endBlock | 子链部署完成区块数   |string  |    |
|erc20Address | erc20合约地址   |string  |    |
|erc20Code | erc20合约编码   |string  |    |
|erc20Hash | erc20合约部署hash   |string  |    |
|failReason | 子链搭建失败原因   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|subchainBaseAddress | 子链合约地址   |string  |    |
|subchainBaseCode | 子链合约编码   |string  |    |
|subchainBaseErcRate | erc20代币与子链代币兑换比率   |string  |    |
|subchainBaseFlushRound | 周期数，40~500之间   |string  |    |
|subchainBaseHash | 子链合约部署hash   |string  |    |
|subchainBaseMax | 子链最多scs数，11、21、31、51、99选择   |string  |    |
|subchainBaseMin | 子链最少scs数，1、3、5、7选择   |string  |    |
|subchainBaseMonitorBond | 注册为monitor最少押金，合约中默认为1MOAC   |string  |    |
|subchainBaseThousandth | 不需修改   |string  |    |
|subchainProtocolAddress | 协议合约地址   |string  |    |
|subchainProtocolBaseCode | 子链协议合约编码   |string  |    |
|subchainProtocolBmin | scs注册最少押金   |string  |    |
|subchainProtocolHash | 协议合约部署hash   |string  |    |
|subchainProtocolName | 子链协议合约名称   |string  |    |
|subchainProtocolType | 0表示pos，1表示ipfs   |string  |    |
|subchainStatus | 子链搭建结果，true:成功，false:失败,空:部署中   |string  |    |
|via | 子链收益账户地址   |string  |    |
|vnodeAddress | vnode合约地址   |string  |    |
|vnodeBmin | 注册vnode最少押金   |string  |    |
|vnodeCode | vnode合约编码   |string  |    |
|vnodeHash | vnode合约部署hash   |string  |    |

**Vnode**

| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | ------------------|--------|----------- |
|createDate | 创建时间   |string  |    |
|createId | 创建人   |string  |    |
|id | 主键   |string  |    |
|isDelete | 是否删除   |string  |    |
|lastUpdateId | 最后修改人   |string  |    |
|lastUpdateTime | 最后修改时间   |string  |    |
|mark | 备注   |string  |    |
|subchainId | 子链id   |string  |    |
|vnodeAddress | 注册vnode使用账户   |string  |    |
|vnodeUrl | vnode链接   |string  |    |

**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«ResponseSubchainData»|
## 3.子链开放注册（切记需在endBlock过至少5个区块后再调用）


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/resisterOpen`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 4.获取实际子链协议池中scs个数


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/scsCount`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 5.scs注册子链节点个数


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/nodeCount`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 6.子链关闭注册


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/resisterClose`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 7.注册成为子链monitor


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/registerAsMonitor`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|monitorUrl| monitor查询地址  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|scsAddress| scs地址  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 8.部署子链dappBase合约

**接口描述**:返回hash为子链交易hash,请使用子链查询交易方法查询

**接口地址**:`/api/moac/subchain/v1/{subchainId}/deployDappBase`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|dappbaseCode| dappbase合约编码  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 9.scs注册入子链协议合约池


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/registerScs`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|scsAddress| scs地址  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 10.子链增加scs,确保scs地址有MOAC


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/registerAdd`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|addNum| 增加数量,默认增加1  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 11.子链重置


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/reset`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 12.子链充值


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/addFund`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|value| 充值MOAC币数量,默认1  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 13.子链关闭


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/{subchainId}/close`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 14.子链信息


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/info/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 15.子链合约列表信息


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/dapplist/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": [],
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |array  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«List«string»»|
## 16.子链基础合约合约的状态


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/dappstate/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 17.子链交易信息


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/transaction/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|hash| 交易hash  | query | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 18.账户在子链上的余额


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/balance/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|address| 账户地址  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
## 18.母链充币到子链（第一步），调用成功会返回hash(使用母链查询交易接口查询)，注：查询交易确认交易成功后调用第二步充值


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/recharge1/{subchainId}`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 充币账户id  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|value| 充币数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 19.母链充币到子链（第二步），调用成功会返回hash(使用母链查询交易接口查询),注：查询交易确认交易成功后，查询子链余额是否到账


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/recharge2/{subchainId}`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 充币账户id  | query | true |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|value| 充币数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 20.子链提币到母链，调用成功会返回hash(使用子链查询交易接口查询)，注：提币交易成功后需要等一段时间才会提币到母链上


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/withdraw/{subchainId}`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 提币账户id  | query | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|value| 提币数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 22.获取账户在子链上的nonce


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/nonce/{subchainId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 账户id  | query | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 23.子链转账，调用成功会返回hash(使用子链查询交易接口查询)


**接口描述**:


**接口地址**:`/api/moac/subchain/v1/transfer/{subchainId}`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 发起账户id  | query | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|nonce| 账户子链nonce,传空则使用当前nonce  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|to| 接收账户  | query | true |string  |    |
|value| 转账数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
# 9.墨客合约类接口

## 1.部署子链业务合约


**接口描述**:


**接口地址**:`/api/moac/subchain/contract/v1/{subchainId}/deploy`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|abi| 合约abi代码  | query | true |string  |    |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|code| 合约编译代码  | query | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 2.子链业务合约注册到dappbase


**接口描述**:


**接口地址**:`/api/moac/subchain/contract/v1/{subchainId}/register`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|abi| 合约abi代码  | query | true |string  |    |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id(部署子链的账户)  | query | true |string  |    |
|code| 合约编译代码  | query | true |string  |    |
|contractAddress| 子链业务合约地址  | query | true |string  |    |
|from| 部署子链业务合约的账户  | query | true |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|payPsw| 支付密码  | query | true |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map»|
## 3.调用交易类合约方法


**接口描述**:


**接口地址**:`/api/moac/subchain/contract/v1/{subchainId}/callTransaction`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|functionName| 合约方法名  | query | true |string  |    |
|inParams| 合约方法入参，如：[{"type":"bool","value":"false"},{"type":"string","value":"1212"},{"type":"address","value":"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60"},{"type":"uint8","value":"10"},{"type":"uint256","value":"10000"}]  | query | false |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|nonce| 账户子链nonce，单次交易可不传  | query | false |string  |    |
|outParams| 合约出参，如：[{"type":"bool"}]  | query | false |string  |    |
|payPsw| 支付密码  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|value| 子链币转账数量  | query | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |object  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«Map«string,string»»|
## 4.调用非交易合约方法


**接口描述**:


**接口地址**:`/api/moac/subchain/contract/v1/{subchainId}/call`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 参数说明     |     in |  是否必须      |  数据类型  |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|accessToken| accessToken  | header | true |string  |    |
|accountId| 墨客账户id  | query | true |string  |    |
|contractAddress| 合约地址  | query | true |string  |    |
|functionName| 合约方法名  | query | true |string  |    |
|inParams| 合约方法入参(多个参数用英文逗号隔开)，如：["字符串入参",true,"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60"]  | query | false |string  |    |
|monitorScsId| scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递）  | query | false |string  |    |
|monitorUrl| scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递）  | query | false |string  |    |
|subchainId| 子链id  | path | true |string  |    |
|walletId| walletId  | header | true |string  |    |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "",
	"success": true
}
```

**响应参数**:


| 参数名称         | 参数说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|code| 代码  |integer(int32)  | integer(int32)   |
|data| 数据  |string  |    |
|message| 信息  |string  |    |
|success| 调用状态  |boolean  |    |





**响应状态**:


| 状态码         | 说明                            |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |ResponseBase«string»|
