# language: pt

Funcionalidade: Iniciar bot de pedidos

  Cenario: 01 - Iniciar bot de pedidos com quantidade 1 - Sucesso
    Dado que seja informado os dados de Order Bot Request
      | Order Quantity |
      | 1              |
    Quando iniciar bot de pedidos
    Entao deveria receber "1" Order Bot Response

  Cenario: 02 - Iniciar bot de pedidos com quantidade 10 - Sucesso
    Dado que seja informado os dados de Order Bot Request
      | Order Quantity |
      | 10             |
    Quando iniciar bot de pedidos
    Entao deveria receber "10" Order Bot Response

  Cenario: 03 - Iniciar bot de pedidos com todos os dados nao informados - Fluxo excepcional
    Dado que seja informado os dados de Order Bot Request
      | Order Quantity |
      |                |
    Quando iniciar bot de pedidos
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                  |
      | BAD_REQUEST | O campo orderQuantity não foi informado. |

  Cenario: 04 - Iniciar bot de pedidos com quantidade menor que 1 - Fluxo excepcional
    Dado que seja informado os dados de Order Bot Request
      | Order Quantity |
      | 0              |
    Quando iniciar bot de pedidos
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                |
      | BAD_REQUEST | O campo orderQuantity deve ser maior que ou igual à 1. |

  Cenario: 05 - Iniciar bot de pedidos com quantidade maior que 1000 - Fluxo excepcional
    Dado que seja informado os dados de Order Bot Request
      | Order Quantity |
      | 1001           |
    Quando iniciar bot de pedidos
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                   |
      | BAD_REQUEST | O campo orderQuantity deve ser menor que ou igual à 1000. |