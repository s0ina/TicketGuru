# Näytä kaikki tapahtumapaikat

**URL**: `/venues`

**Metodi**: `GET`

**Vaadittu rooli**: -

## Vastauksen paluukoodi

**Koodit**: 

`200 OK`

## Esimerkkivastaus:

**Polku**: `BASE_URL/venues`

```json
[
  {
    "venueId": 1,
    "venueName": "tapahtumapaikka1",
    "venueDescription": "pieni paikka",
    "address": "Maitokatu 1",
    "postalCode": {
      "postalCode": "00100",
      "city": "Helsinki"
    }
  },
  {
    "venueId": 2,
    "venueName": "tapahtumapaikka2",
    "venueDescription": "iso paikka",
    "address": "Piimätie 34",
    "postalCode": {
      "postalCode": "33101",
      "city": "Tampere 10"
    }
  }
]
```
