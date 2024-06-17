# medAPI
	
## Requisitos 
- [x] Desenvolvido utilizando o framework Spring;
- [ ] Possui:
    - [ ] entre três e sete `models`;
    - [x] dois ou três `services`;
    - [x] dois ou três `controllers`;
- [x] Foi disponibilizado em repositório git público e acessível;
- [ ] Pode ser executado a partir de um container docker configurado;
- [ ] Possui um `README.md` com toda a documentação necessária para executar e manipular a aplicação (exemplos de requisições);
- [ ] Fornece configurações para execução rápida;
- [ ] Possui código bem identado e projeto organizado;
- [ ] Faz a paginação de entidades no `GET ALL`;
- [x] Valida e trata de erros do cliente;
- [x] Segue os princípios `REST`;

**Equipe**: Kauan Fontanela e Lucas Adriano

## ROUTES
### `GET` medicines
```jsonc
[
	{
		"name": "string",
		"needsPrescription": true | false,
    	"treatsFor": [
			"sympton1", 
			"sympton2", 
			...
		]
	},
	...
]
```

### sympton
```jsonc
{
	"name": "string",	
}
```