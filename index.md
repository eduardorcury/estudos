---
layout: default
title: Meus Estudos
---

![d37c900d754bbbe90654605b3b94a8d4.gif](.gitbook/assets/d37c900d754bbbe90654605b3b94a8d4.gif)

# 📚 Índice de Estudos

{% assign folders = site.static_files | map: "path" | uniq %}

<ul>
  {% for folder in site.pages %}
    {% if folder.path contains "/" and folder.name != "index.md" %}
      <li>
        <a href="{{ folder.url | relative_url }}">{{ folder.path }}</a>
      </li>
    {% endif %}
  {% endfor %}
</ul>