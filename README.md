# vinicius562003 — Cidade em Dia de Chuva

## Integrantes

| Nome      | RM     |
|-----------|--------|
| Miguel    | 556501 |
| Guilherme | 564643 |
| Vinicius  | 562003 |

**RM da equipe:** 556501 + 564643 + 562003 = **1683147**

## Parâmetros

| Parâmetro | Cálculo | Resultado |
|-----------|---------|-----------|
| P1 · prefixo das tabelas | 1683147 % 1000 = 147 | GS147 |
| P2 · limiar de alerta L | 10 + 2 × (1683147 % 4) = 10 + 2 × 3 | L = 16 cm |
| P3 · atributo extra de Bairro | (1683147 / 10) % 3 = 2 | possuiPiscinao (Boolean) |
| P4 · ordenação dos trechos | (1683147 / 100) % 3 = 1 | altitude, depois logradouro |
| P5 · exclusão de trecho com leituras | (1683147 / 1000) % 2 = 1 | cascata |

## Versão

Versão web (HTML), com Thymeleaf.

## DTOs

Um DTO por entidade (`BairroDTO`, `TrechoDTO`, `LeituraDTO`), como classes comuns, mais o `PainelItemDTO` e o `PainelDTO` como `record`.
