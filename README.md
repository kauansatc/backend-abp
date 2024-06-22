# medAPI

<div align="center" style="margin: 2rem">
	<img src="img/medicine-health-medical-drug-pharmacy-pill-capsule-svgrepo-com.svg" width=100>
	<div>
		API de consulta e relação de remédios e síntomas. 
	</div>
	<i style="color: navy">
		Equipe: Kauan Fontanela e Lucas Adriano
	</i>
</div>


---


## ROTAS
### `POST` /medicines
Cria um novo remédio

#### body
```jsonc
[
	{
		"name": "string",
		"needsPrescription": true | false,
    	"treatsFor": [ "sympton1", "sympton2", ... ]
	},
	[...]
]
```

#### query options
|param.|valores|descrição|	
|---|---|---|
|handleNew|`true`, `false`|Em caso de tratar síntomas ainda não registrados, os registra na base de dados|

#### retorna
```jsonc
// status 200
"mensagem de sucesso"

// status 400
"descrição do erro"
```


---


### `GET` medicines

Lista dos remédios registrados

#### retorna 
```jsonc
// status 200
[
	{
		"name": "string",
		"needsPrescription": true | false,
    	"treatsFor": [ "sympton1", "sympton2", ... ]
	},
	[...]
]

// status 400
"descrição do erro"
```

#### Query
|param.|valores|descrição|	
|---|---|---|
|page|`>=0`|Página a ser exibida|
|size|`>0`|Número de elementos por página|


---


### `PATCH` /medicines/`{nome}`
Altera as propriedades do remédio definido na rota `{nome}`.

> Todos as propriedades do request são opcionais (podem ser `null` ou simplesmente não serem inclusas no request).

> No parâmetro `treatsFor`, todos os sintomas não inclusos no request serão desassociados deste remédio, respeitando a regra de que todo remédio deve tratar ao menos 1 sintoma.


#### body
```jsonc
[
	{
		"name": "string" | null,
		"needsPrescription": true | false | null,
    	"treatsFor": [ "sympton1", "sympton2", ... ] | null
	},
	...
]
```

#### query options
|param.|valores|descrição|	
|---|---|---|
|handleNew|`true`, `false`|Em caso de tratar síntomas ainda não registrados, os registra na base de dados|

#### retorna
```jsonc
// status 200
"mensagem de sucesso"

// status 400
"descrição do erro"
```


---


### `DELETE` /medicines/`{nome}`
Apaga o remédio definido na rota `{nome}`.

#### retorna
```jsonc
// status 200
"mensagem de sucesso"

// status 400
"descrição do erro"
```


---


### `GET` sympton

Lista dos sintomas registrados

#### retorna 
```jsonc
// status 200
[
	"sympton1",
	"sympton2",
	[...]
]

// status 400
"descrição do erro"
```

#### Query
|param.|valores|descrição|	
|---|---|---|
|page|`>=0`|Página a ser exibida|
|size|`>0`|Número de elementos por página|
|medicine|`"name"`|Filtrar sintoma por remédios (elimina a paginação)|


---


### `DELETE` /symptons/`{nome}`
Apaga o síntoma definido na rota `{nome}`.
> Todo remédio deve tratar ao menos um síntoma, caso haja remédios que tratem exclusivamente deste síntoma, a API retornará um erro. Para contornar este erro, use o query `force=true`, que excluíra, também, os remédios exclusivamente dependentes.

#### Query
|param.|valores|descrição|	
|---|---|---|
|force|`true`,`false`|Apagar, também, remédios exclusivamentes dependentes deste síntoma|

#### retorna
```jsonc
// status 200
"mensagem de sucesso"

// status 400
"descrição do erro"
```