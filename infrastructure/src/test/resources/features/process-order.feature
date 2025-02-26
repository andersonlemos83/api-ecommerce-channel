# language: pt

Funcionalidade: Processar Pedido

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 48     | 06     |

  Cenario: 01 - Processar pedido valido qualquer - Sucesso
    Dado que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654322    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document       | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code  | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048-434 | (82) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint authorize-sale
      | Status | Response                                  |
      | OK     | /fixtures/OrderResponseDto-987654322.json |
    Quando processar pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                   |
      | /fixtures/OrderDocument-987654322.json |
    E deveria enviar para o endpoint authorize-sale os requests esperados
      | Request                                  |
      | /fixtures/OrderRequestDto-987654322.json |

  Cenario: 02 - Processar pedido valido qualquer que ja possui nota fiscal - Fluxo alternativo
    Dado que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654323    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document       | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code  | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048-434 | (82) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654323.json |
    Quando processar pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                   |
      | /fixtures/OrderDocument-987654323.json |
    E nao deveria enviar nenhum request para o endpoint authorize-sale

  Cenario: 03 - Processar pedido valido qualquer com endpoint authorize-sale offline - Fluxo excepcional
    Dado que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654324    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document       | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code  | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048-434 | (82) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    Quando processar pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                   |
      | /fixtures/OrderDocument-987654324.json |
    E deveria enviar para o endpoint authorize-sale os requests esperados
      | Request                                  |
      | /fixtures/OrderRequestDto-987654324.json |

  Cenario: 04 - Processar pedido invalido qualquer sem todos os campos obrigatorios - Fluxo excepcional
    Dado que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          |              | 100        | 105 | 987654325    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document       | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code  | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048-434 | (82) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint authorize-sale
      | Status      | Response                                  |
      | BAD_REQUEST | /fixtures/ErrorResponseDto-987654325.json |
    Quando processar pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                   |
      | /fixtures/OrderDocument-987654325.json |
    E deveria enviar para o endpoint authorize-sale os requests esperados
      | Request                                  |
      | /fixtures/OrderRequestDto-987654325.json |

  Cenario: 05 - Processar pedido valido qualquer com erro inesperado ao consultar a base de dados - Fluxo excepcional
    Dado que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654326    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document       | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code  | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048-434 | (82) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    Quando processar pedido
    Entao nao deveria existir nenhum Order Document na base
    E nao deveria enviar nenhum request para o endpoint authorize-sale

  Cenario: 06 - (Re)Processar pedido valido qualquer que ja esta com status de erro - Sucesso
    Dado que seja gerado os seguintes dados de Order Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654327    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document       | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code  | Phone           | Email                        |
      | Martin Kauê Lopes | 607.785.324-02 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048-434 | (82) 99234-4475 | martin_lopes@rafaelmarin.net |
    E que seja gerado os seguintes dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja gerado os seguintes dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint authorize-sale
      | Status | Response                                  |
      | OK     | /fixtures/OrderResponseDto-987654327.json |
    E que existam as Order Document cadastradas
      | Json                                         |
      | /fixtures/OrderDocument-987654327-error.json |
    Quando processar pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                   |
      | /fixtures/OrderDocument-987654327.json |
    E deveria enviar para o endpoint authorize-sale os requests esperados
      | Request                                  |
      | /fixtures/OrderRequestDto-987654327.json |