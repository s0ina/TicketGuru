# Muokkaa id:llä haettua lipputyyppiä

**URL**: `/tickettypes/:pk`

**Metodi**: `PUT`

**Vaadittu rooli**: Admin

## Vastauksen paluukoodi

**Koodit**:

`200 OK`

`201 Created`

`204 No content`

## Esimerkkipyyntö:

**Polku**: `BASE_URL/tickettype/4`

```json
{
  "typeId": 4,
  "typeName": "LapsiMUOKATTU"
}

```

## Esimerkkivastaus:

```json
{
    "typeId": 4,
    "typeName": "LapsiMUOKATTU"
}

```
