# language: pt

Funcionalidade: Processar Callback Pedido

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 49     | 30     |

  Cenario: 01 - Processar callback pedido valido com status de processado - Sucesso
    Dado que seja informado os dados de Order Callback Request
      | Order Number | Invoice Key                                  | Invoice Number | Issuance Date       | Invoice Base64                                                           | Status    | Error Reason |
      | 987654351    | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:49:00 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              |
    E que existam as Order Document cadastradas
      | Json                                                   |
      | /fixtures/OrderDocument-987654351-invoice-pending.json |
    Quando processar callback pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                            |
      | /fixtures/OrderDocument-987654351-invoiced.json |
    E deveria enviar os e-mails esperados
      | Email To Regex               | Email From Regex   | Email Subject                                           | Email Body                        |
      | martin_lopes@rafaelmarin.net | no-reply@gmail.com | Email de Nota Fiscal - E-Commerce Digital Fictício Ltda | /fixtures/EmailBody-987654351.txt |

  Cenario: 02 - Processar callback pedido valido com status de erro - Sucesso
    Dado que seja informado os dados de Order Callback Request
      | Order Number | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status | Error Reason                                               |
      | 987654352    |             |                |               |                | ERROR  | O valor total dos itens está diferente do total informado. |
    E que existam as Order Document cadastradas
      | Json                                                   |
      | /fixtures/OrderDocument-987654352-invoice-pending.json |
    Quando processar callback pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                         |
      | /fixtures/OrderDocument-987654352-error.json |
    E nao deveria enviar nenhum e-mail

  Cenario: 03 - Processar callback pedido nao existente na base de dados - Fluxo alternativo
    Dado que seja informado os dados de Order Callback Request
      | Order Number | Invoice Key                                  | Invoice Number | Issuance Date       | Invoice Base64                                                           | Status    | Error Reason |
      | 987654353    | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:49:00 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              |
    Quando processar callback pedido
    Entao nao deveria existir nenhum Order Document na base
    E nao deveria enviar nenhum e-mail

  Cenario: 04 - Processar callback pedido com status em processamento - Fluxo alternativo
    Dado que seja informado os dados de Order Callback Request
      | Order Number | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status        | Error Reason |
      | 987654354    |             |                |               |                | IN_PROCESSING |              |
    E que existam as Order Document cadastradas
      | Json                                                   |
      | /fixtures/OrderDocument-987654354-invoice-pending.json |
    Quando processar callback pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                                   |
      | /fixtures/OrderDocument-987654354-invoice-pending.json |
    E nao deveria enviar nenhum e-mail

  Cenario: 05 - Processar callback pedido valido com erro inesperado ao consultar a base de dados - Fluxo excepcional
    Dado que seja informado os dados de Order Callback Request
      | Order Number | Invoice Key                                  | Invoice Number | Issuance Date       | Invoice Base64                                                           | Status    | Error Reason |
      | 987654355    | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:49:00 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              |
    E que existam as Order Document cadastradas
      | Json                                                   |
      | /fixtures/OrderDocument-987654355-invoice-pending.json |
    Quando processar callback pedido
    Entao deveria existir as seguintes Order Document na base
      | Json                                                   |
      | /fixtures/OrderDocument-987654355-invoice-pending.json |
    E nao deveria enviar nenhum e-mail