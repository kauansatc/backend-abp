# medAPI
**Equipe**: Kauan Fontanela e Lucas Adriano

## models
- medicine
	- name
    - treats_for

- prescription 
	- medicine
	- dose
	- interval 
	- full_period

- disease
	- name
	- symptons         

## services 
- disease
	- diagnosis

- prescption
    - treat_for

## controller
- medicines CRUD
	- GET
	- POST
	- PUT
	- DELETE
- consult
	- GET disease q=symptons r=possible_diseases
	- GET treatment q=possible_diseases r=best_treatment