# DMZ Form Options

Addon para o **DragonMine Z 2.2** (Forge 1.20.1) que acrescenta opções novas aos JSON das transformações
(`config/dragonminez/races/<raça>/forms/<grupo>.json`, dentro de cada forma). Os nomes das opções estão em inglês, como os do DMZ.
Todas têm um valor padrão que não muda nada: só quem escreve a opção no JSON vê efeito.

Precisa do addon no cliente (e no servidor, se jogar em servidor, para a config sincronizar).

## Opções

| Opção | Tipo / padrão | O que faz |
|---|---|---|
| `forceTiedTail` | boolean / `false` | A cauda fica amarrada na cintura nessa forma (útil no SSJ3, em que a cauda some dentro do cabelo). |
| `forceLooseTail` | boolean / `false` | A cauda fica solta nessa forma (útil no SSJ4). Se as duas estiverem `true`, a solta vence. |
| `stackTintAffectsHairAndTail` | boolean / `true` | Vale na forma **base** (ex.: Blue). Com `false`, a tinta de uma forma empilhável (Kaio-ken) **não** pinta cabelo e cauda. |
| `stackTintHairAndTailIntensity` | número / `-1` | Vale na forma **base**. De 0 a 1: intensidade da tinta da forma empilhável **no cabelo, na cauda e nas sobrancelhas**. `-1` = usa a intensidade da própria forma empilhável. |
| `stackTintColor` | texto / `""` | Vale na forma **base**. Cor da tinta da forma empilhável **na pele** (corpo). `""` ou `"#"` = usa a cor da própria forma empilhável. |
| `stackTintIntensity` | número / `-1` | Vale na forma **base**. De 0 a 1: intensidade da tinta da forma empilhável **na pele**. `-1` = usa a intensidade da própria forma empilhável; `0` = sem tinta na pele. |
| `stackOutlinePrimaryColor` | texto / `""` | Vale na forma **base**. Cor **primária** do outline enquanto uma forma empilhável está ativa. `""` ou `"#"` = usa a cor primária do outline da própria forma empilhável. |
| `stackOutlineThicknessMultiplicator` | número / `1.0` | Vale na forma **base**. Multiplica a espessura do outline **da forma empilhável** (cada nível com a sua). `2.0` = o dobro, `1.0` = igual. |
| `stackOutlineColorMode` | texto / `""` | Vale na forma **base**. `"pulse"` ou `"gradient"` para o outline da forma empilhável. `""` = usa o modo do próprio outline da empilhável. |
| `scleraColor` | texto / `""` | Cor da esclera (parte branca do olho), por exemplo `"#FFFFFF"`. Vazio = cor padrão do jogo. |
| `noEyebrows` | boolean / `false` | Esconde as sobrancelhas: não desenha a camada de sobrancelha e usa a textura `ssj3eyebrows_eye_N` do DMZ (a do SSJ3) por cima, escolhida pelo tipo de olho do jogador. |
| `eyeliner` | boolean / `false` | Desenha o delineado nos olhos (usa a textura `ssj4_eyes_N` do DMZ, escolhida pelo tipo de olho do jogador). |
| `eyelinerColor` | texto / `"#000000"` | Cor do delineado. |
| `chargeKiAnimation` | boolean / `true` | Com `false`, o jogador **não** faz a pose de carregar ki: fica parado (idle) enquanto a aura carrega. |

## Exemplo

```json
"supersaiyan3": {
  "forceTiedTail": true,
  "noEyebrows": true
},
"supersaiyan4": {
  "forceLooseTail": true,
  "eyeliner": true,
  "eyelinerColor": "#B00000"
},
"supersaiyanblue": {
  "stackTintAffectsHairAndTail": true,
  "stackTintHairAndTailIntensity": 0.1,
  "stackTintColor": "",
  "stackTintIntensity": -1.0,
  "stackOutlinePrimaryColor": "#FF2A2A",
  "stackOutlineThicknessMultiplicator": 2.0,
  "stackOutlineColorMode": "gradient"
},
"masteredultrainstinct": {
  "chargeKiAnimation": false,
  "scleraColor": "#FFFFFF"
}
```

(O nome da forma é a chave do objeto dentro de `"forms"`; coloque as opções junto das outras, por exemplo perto de `"hideTail"`.)

Depois de salvar, use `/dmzreload config`.

## Observações

- **Ordem no arquivo:** se o DMZ regravar o arquivo (atualização de versão), as opções novas vão para o fim de cada forma, não ao lado do `hideTail`.
- **Olhos:** esclera, sobrancelha e delineado valem para o rosto de estilo humano/saiyajin. Raças com rosto próprio (Namekuseijin, Freeza etc.) não usam essas opções.
- **Delineado:** só aparece se existir `ssj4_eyes_N.png` para o tipo de olho do jogador (o DMZ já traz as do SSJ4).
- **Tinta do Kaio-ken:** a opção vai na forma **base**. Afeta o cabelo, a cauda desenhada na camada do corpo e as partes de raça (cauda, antenas). As sobrancelhas (cor do cabelo no rosto) também usam a tinta do cabelo/cauda. Os olhos continuam com a tinta normal.
- **Mixins:** o addon altera classes do DMZ 2.2. Se uma opção não fizer nada, veja o `logs/latest.log` e procure por `dmz_form_options` (o jogo abre mesmo se algum mixin falhar).
- Não foi testado dentro do jogo.

## Compatibilidade com o DMZ Face Animations (dmz-blink)

O dmz-blink troca o caminho da textura dentro de `renderColoredLayer`. Este addon desenha os olhos pelo mesmo método
(esclera, sobrancelha, delineado), então a piscada e os olhos angulares do dmz-blink devem continuar valendo com as
opções daqui. Com `noEyebrows` o addon nem desenha a camada de sobrancelha (que o dmz-blink troca por versões angulares/piscando) e ainda aplica o apagador do SSJ3 por cima. Não foi testado junto.

## Se o cabelo não mudar com a tinta

Ao carregar o jogo, procure no `logs/latest.log` a linha `hair/tail tint override is active`. Se ela aparece, o addon
está trocando a tinta do cabelo; se o cabelo continua igual, o estilo de cabelo provavelmente tem cores próprias por
mecha, que o DMZ não tinge. Se a linha não aparece, a opção não foi lida (confira a forma base e `/dmzreload config`).

## v1.1.2

- O cabelo agora recebe a tinta também quando o estilo tem **cor própria por mecha** (o DMZ ignora a tinta global nesse caso). Mechas que herdam a cor global não são tintadas duas vezes.

## v1.1.3

- Corrigido: os mixins do cabelo e das partes da raça não eram aplicados (o método era `static` e o do DMZ não é). Por isso a tinta nunca chegava no cabelo.

## v1.2.0 — opções da forma base para formas empilháveis (Kaio-ken)

As opções novas ficam na forma **base** (ex.: Blue) e dizem como uma forma empilhável (Kaio-ken) fica **em cima dela**. Nada muda se você não escrever as opções, e sem forma base ativa (Kaio-ken sozinho) vale a configuração da própria forma empilhável.

- **Pele:** `stackTintColor` e `stackTintIntensity`. Com `""` e `-1`, a pele usa a tinta da própria forma empilhável.
- **Cabelo, cauda e sobrancelhas:** continuam em `stackTintAffectsHairAndTail` e `stackTintHairAndTailIntensity`, sempre com a **cor** da forma empilhável.
- **Outline:** `stackOutlinePrimaryColor`, `stackOutlineThicknessMultiplicator` e `stackOutlineColorMode`. Quando uma delas está preenchida (cor diferente de vazio, multiplicador diferente de `1.0` ou modo diferente de vazio), o outline é o da própria forma empilhável, com estas trocas:
  - cor **primária** = `stackOutlinePrimaryColor` (vazio ou `"#"` = primária da forma empilhável);
  - espessura = espessura da forma empilhável × `stackOutlineThicknessMultiplicator` (cada nível do Kaio-ken multiplica o seu valor);
  - modo de cor = `stackOutlineColorMode` (vazio = o da forma empilhável).

  Cor secundária, fogo e brilho vêm sempre da forma empilhável, que precisa ter `outlineShader.enabled: true`. Com tudo no padrão, vale o comportamento original do DMZ.
- **Tinta x2 → x20:** cada nível do Kaio-ken é uma forma separada com seu próprio `tintIntensity`. Deixe `stackTintIntensity` e `stackTintHairAndTailIntensity` em `-1` para cada nível usar o seu. Um valor fixo vale para todos os níveis.
- Removidas as opções da 1.2.0 anterior (`tintColor` vazio, `primaryColor` vazio e `addToBaseOutline`).

Exemplo (na forma base):
```json
"stackTintColor": "",
"stackTintIntensity": -1.0,
"stackTintHairAndTailIntensity": -1.0,
"stackOutlinePrimaryColor": "#FF2A2A",
"stackOutlineThicknessMultiplicator": 2.0,
"stackOutlineColorMode": "gradient"
```
