# Lisää uusi lipputyyppi

**URL**: `/tickettypes`

**metodi**: `POST`

**Vaadittu rooli**: Admin

## Vastauksen paluukoodit

**Koodit**:

`201 Created`

## Esimerkkipyyntö:


**Polku**: `BASE_URL/events`


```json
{ 
  "typeName": "Varusmies"
}

```

## Esimerkkivastaus:


```json
{
    "typeId": 3,
    "typeName": "Varusmies"
}

```
