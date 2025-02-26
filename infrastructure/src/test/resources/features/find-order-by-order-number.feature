# language: pt

Funcionalidade: Consultar pedido por Order Number

  Cenario: 01 - Consultar pedido valido qualquer existente na base de dados - Sucesso
    Dado que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654371.json |
      | /fixtures/OrderDocument-987654372.json |
      | /fixtures/OrderDocument-987654373.json |
    Quando consultar pedido por order number "987654372"
    Entao deveria receber os dados de Full Order Finder Response
      | Json                                                |
      | /fixtures/FullOrderFinderResponseDto-987654372.json |

  Cenario: 02 - Consultar pedido valido qualquer inexistente na base de dados - Fluxo excepcional
    Dado que existam as Order Document cadastradas
      | Json                                   |
      | /fixtures/OrderDocument-987654371.json |
      | /fixtures/OrderDocument-987654372.json |
      | /fixtures/OrderDocument-987654373.json |
    Quando consultar pedido por order number "987654374"
    Entao deveria receber os dados de Error Response
      | Http Status | Message                      |
      | BAD_REQUEST | O pedido não foi encontrado. |