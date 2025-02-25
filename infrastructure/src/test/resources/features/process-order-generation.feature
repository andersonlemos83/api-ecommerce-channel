# language: pt

Funcionalidade: Processar Geracao Pedido

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 48     | 06     |

  Cenario: 01 - Processar geracao pedido valido - Sucesso
    Dado que seja informado os dados de Order Generator Request
      | Order Number |
      | 987654321    |
    E que seja gerado os seguintes CEPs
      | 57048434 |
    E que existam os seguintes responses disponiveis no endpoint findByCep
      | Key      | Status | Response                                   |
      | 57048434 | OK     | /fixtures/AddressResponseDto-57048434.json |
    E que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 105.04      | 5.05          |
    E que seja gerado os seguintes dados de Customer
      | Name              | Document       | Document Type | Address Number | Country | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | 622            | Brasil  | (XX) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 270606             | 3556777163651312 |         | 105.04 |
    Quando processar geracao pedido
    Entao deveria publicar o JSON esperado na fila
      | Queue Name  | Json Key  |
      | order-queue | 987654321 |

  Cenario: 02 - Processar geracao pedido com CEP nao existente - Fluxo excepcional
    Dado que seja informado os dados de Order Generator Request
      | Order Number |
      | 987654322    |
    E que seja gerado os seguintes CEPs
      | 57048435 |
    E que existam os seguintes responses disponiveis no endpoint findByCep
      | Key      | Status | Response                                   |
      | 57048435 | OK     | /fixtures/AddressResponseDto-57048435.json |
    E que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 105.04      | 5.05          |
    E que seja gerado os seguintes dados de Customer
      | Name              | Document       | Document Type | Address Number | Country | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | 622            | Brasil  | (XX) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 270606             | 3556777163651312 |         | 105.04 |
    Quando processar geracao pedido
    Entao nao deveria publicar nenhum JSON na fila
      | Queue Name  |
      | order-queue |

  Cenario: 03 - Processar geracao pedido com CEP invalido - Fluxo excepcional
    Dado que seja informado os dados de Order Generator Request
      | Order Number |
      | 987654323    |
    E que seja gerado os seguintes CEPs
      | 57048-436 |
    E que existam os seguintes responses disponiveis no endpoint findByCep
      | Key       | Status      | Response                   |
      | 57048-436 | BAD_REQUEST | Http 400 - Verifique a URL |
    E que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 105.04      | 5.05          |
    E que seja gerado os seguintes dados de Customer
      | Name              | Document       | Document Type | Address Number | Country | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | 622            | Brasil  | (XX) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 270606             | 3556777163651312 |         | 105.04 |
    Quando processar geracao pedido
    Entao nao deveria publicar nenhum JSON na fila
      | Queue Name  |
      | order-queue |