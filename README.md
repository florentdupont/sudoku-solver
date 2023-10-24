## Sudoku Solver

Exercice de résolution de Sudoku réalisé en tant que Puzzle pour s'occuper les soirées d'hiver.

En vrac : 
- Utilise Kotlin.
- Le jeu a été construit de manière incrémentale, d'abord sur les puzzles faciles, puis en complétant au fur et à mesure pour résoudre les niveaux difficile, expert et diabolique.
- Résout les Sudoku en niveau "Diabolique" du site sudoku.com
- réalise en plusieurs passes en tentant des alternatives (brute-force) dès que l'analyse par simple élimination n'est plus possible.
- 1 première approche de résolution en Depth-first étant problématique, la résolution a été revue en Breadth-first avec l'utilisation d'une file.
- Lorsque la résolution est bloquée, une approche brute-force est réalisée : Chaque alternative est poussée dans un file et les solutions sont tentées au fur et à mesure.
- Les solutions amenant à des configurations impossibles ou des mouvements illégaux sont exclues.

