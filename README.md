# CLI-RERANKER+SEARCH

[English version](README.en.md)

```declarative
   ________    ____     ____                        __                       _____                      __  
  / ____/ /   /  _/    / __ \___  _________ _____  / /_____  _____    __    / ___/___  ____ ___________/ /_ 
 / /   / /    / /_____/ /_/ / _ \/ ___/ __ `/ __ \/ //_/ _ \/ ___/ __/ /_   \__ \/ _ \/ __ `/ ___/ ___/ __ \
/ /___/ /____/ /_____/ _, _/  __/ /  / /_/ / / / / ,< /  __/ /    /_  __/  ___/ /  __/ /_/ / /  / /__/ / / /
\____/_____/___/    /_/ |_|\___/_/   \__,_/_/ /_/_/|_|\___/_/      /_/    /____/\___/\__,_/_/   \___/_/ /_/ 
                                                                                                            


CLI-Reranker + Search 1.0.0 -- Service for ranking films by genre and searching by keywords

USAGE

  $ run <command>

COMMANDS

  - all              -- Get all movies
  - rank <genre>     -- Rank movies by your favorite genre
  - search <query>   -- Search movies by keywords
```

### Команды и примеры их использования:
1. Получить список всех фильмов:
```shell
run all
```
```declarative
Movies (5):
1. The Revenant — [thriller, adventure]
2. John Wick — [thriller, crime]
3. Green Book — [drama]
4. 1+1 — [drama, comedy]
5. The Martian — [adventure, fantasy]
```

2. Ранжировать фильмы по жанру _(MMR алгоритм)_:
```shell
run rank comedy
```
```declarative
Movies (5):
1. 1+1 — [drama, comedy]
2. The Revenant — [thriller, adventure]
3. John Wick — [thriller, crime]
4. The Martian — [adventure, fantasy]
5. Green Book — [drama]
```

3. Выполнить поиск по ключевым словам _(TF-IDF алгоритм)_:
```shell
run search planet
```
```declarative
Movies (1):
1. The Martian — [adventure, fantasy]
```

### Инструменты и зависимости:
* Scala
* ZIO
* ZIO-CLI
* Service Pattern
* DI
* MMR algorithm
* TF-IDF algorithm
