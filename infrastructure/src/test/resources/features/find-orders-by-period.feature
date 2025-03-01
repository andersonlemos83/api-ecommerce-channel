# language: pt

Funcionalidade: Consultar pedidos por periodo

  Cenario: 01 - Consultar pedidos existentes na base de dados entre 2025-02-25 e 2025-02-27 - Sucesso
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      | 2025-02-25        | 2025-02-27      | 0           | 20        |
    E que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654381.json |
      | /fixtures/OrderDocument-987654382.json |
      | /fixtures/OrderDocument-987654383.json |
      | /fixtures/OrderDocument-987654384.json |
      | /fixtures/OrderDocument-987654385.json |
    Quando consultar pedidos por periodo
    Entao deveria receber os dados de Half Order Finder Response
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date       | Status          | Error Reason                                               | Created Date        | Updated Date        |
      | SLF          | 403          | 843        | 518 | 987654384    | 2598.66     | 25.73         |                                              |                |                     | ERROR           | O valor total dos itens está diferente do total informado. | 2025-02-27T12:21:55 | 2025-02-27T12:21:55 |
      | APP          | 981          | 761        | 909 | 987654383    | 2246.96     | 22.25         | 27250212345678550010000000011234567898765432 | 000000083      | 2025-02-26T12:22:55 | INVOICED        |                                                            | 2025-02-26T12:21:55 | 2025-02-26T12:23:55 |
      | APP          | 150          | 129        | 830 | 987654382    | 28.07       | 0.28          |                                              |                |                     | INVOICE_PENDING |                                                            | 2025-02-25T12:21:54 | 2025-02-25T12:21:54 |

  Cenario: 02 - Consultar pedidos existentes na base de dados entre 2025-02-26 e 2025-02-26 - Sucesso
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      | 2025-02-26        | 2025-02-26      | 0           | 20        |
    E que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654381.json |
      | /fixtures/OrderDocument-987654382.json |
      | /fixtures/OrderDocument-987654383.json |
      | /fixtures/OrderDocument-987654384.json |
      | /fixtures/OrderDocument-987654385.json |
    Quando consultar pedidos por periodo
    Entao deveria receber os dados de Half Order Finder Response
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date       | Status   | Error Reason | Created Date        | Updated Date        |
      | APP          | 981          | 761        | 909 | 987654383    | 2246.96     | 22.25         | 27250212345678550010000000011234567898765432 | 000000083      | 2025-02-26T12:22:55 | INVOICED |              | 2025-02-26T12:21:55 | 2025-02-26T12:23:55 |

  Cenario: 03 - Consultar pedidos nao existentes na base de dados - Sucesso
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      | 2025-02-20        | 2025-02-23      | 0           | 20        |
    E que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654381.json |
      | /fixtures/OrderDocument-987654382.json |
      | /fixtures/OrderDocument-987654383.json |
      | /fixtures/OrderDocument-987654384.json |
      | /fixtures/OrderDocument-987654385.json |
    Quando consultar pedidos por periodo
    Entao nao deveria receber nenhum Half Order Finder Response

  Cenario: 04 - Consultar pedidos existentes na base de dados com periodo invalido - Fluxo excepcional
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      | 2025-02-27        | 2025-02-25      | 0           | 20        |
    E que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654381.json |
      | /fixtures/OrderDocument-987654382.json |
      | /fixtures/OrderDocument-987654383.json |
      | /fixtures/OrderDocument-987654384.json |
      | /fixtures/OrderDocument-987654385.json |
    Quando consultar pedidos por periodo
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                              |
      | BAD_REQUEST | O período de 27/02/2025 00:00:00 até 25/02/2025 23:59:59 é inválido. |

  Cenario: 05 - Consultar pedidos com todos os dados nao informados - Fluxo excepcional
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      |                   |                 |             |           |
    Quando consultar pedidos por periodo
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                                                                                                                       |
      | BAD_REQUEST | O campo endPeriodDate não foi informado, O campo pageNumber não foi informado, O campo pageSize não foi informado, O campo startPeriodDate não foi informado. |

  Cenario: 06 - Consultar pedidos com dados invalidos - Fluxo excepcional
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      | 2025-02-25        | 2025-02-27      | -1          | 9         |
    Quando consultar pedidos por periodo
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                                                                |
      | BAD_REQUEST | O campo pageNumber deve ser maior que ou igual à 0, O campo pageSize deve ser maior que ou igual à 10. |

  Cenario: 07 - Consultar pedidos existentes na base de dados entre 2025-02-25 e 2025-02-27 com paginacao - Sucesso
    Dado que seja informado os dados de Order Finder Request
      | Start Period Date | End Period Date | Page Number | Page Size |
      | 2025-02-25        | 2025-02-27      | 2           | 20        |
    E que existam "45" Order Document cadastradas
    Quando consultar pedidos por periodo
    Entao deveria receber "5" Half Order Finder Response