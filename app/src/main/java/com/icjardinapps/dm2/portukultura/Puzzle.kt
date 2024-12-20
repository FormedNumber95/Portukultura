package com.icjardinapps.dm2.portukultura

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Puzzle : AppCompatActivity() {

    private lateinit var puzzleGrid: GridLayout
    private lateinit var imagenFondo:ImageView
    private val puzzlePieces = Array(16) { 0 } // Almacena las imágenes de las piezas
    private var emptyIndex = 15 // Comienza con el índice del espacio vacío

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.puzzle)
        imagenFondo=findViewById(R.id.backgroundImage)
        imagenFondo.scaleType=ImageView.ScaleType.FIT_XY
        puzzleGrid = findViewById(R.id.puzzleGrid)

        initializePuzzle() // Configura el puzzle al iniciar
    }

    private fun initializePuzzle() {
        // Carga las imágenes de las piezas
        for (i in 0 until 15) {
            val resourceId = resources.getIdentifier("puzle_${i + 1}", "drawable", packageName)
            puzzlePieces[i] = resourceId
        }
        // Última pieza es el hueco blanco
        puzzlePieces[15] = R.drawable.puzle_blanco

        // Mezclar las piezas de manera que el puzzle sea resolvible
        do {
            puzzlePieces.shuffle()
        } while (!isSolvable(puzzlePieces) || puzzlePieces[15] != R.drawable.puzle_blanco)

        // Encuentra el índice del espacio vacío en la disposición inicial
        emptyIndex = puzzlePieces.indexOf(R.drawable.puzle_blanco)

        updateGrid()
    }

    private fun updateGrid() {
        puzzleGrid.removeAllViews()
        for (i in 0 until 16) {
            val imageView = ImageView(this)
            if (puzzlePieces[i] == R.drawable.puzle_blanco) {
                imageView.setBackgroundColor(0x00000000) // Color transparente
            } else {
                imageView.setImageResource(puzzlePieces[i])
            }
            imageView.setOnClickListener { onTileClick(i) }
            val params: GridLayout.LayoutParams = GridLayout.LayoutParams().apply {
                width = 0 // Esto es para que se ajuste proporcionalmente en el Grid
                height = 0 // Igual para la altura
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)  // Ajuste proporcional de columna
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)  // Ajuste proporcional de fila
            }

            imageView.layoutParams=params
            imageView.scaleType=ImageView.ScaleType.FIT_XY
            puzzleGrid.addView(imageView)
        }
    }

    private fun onTileClick(index: Int) {
        if (isAdjacentToEmpty(index)) {
            // Intercambia la pieza con el hueco vacío
            puzzlePieces[emptyIndex] = puzzlePieces[index]
            puzzlePieces[index] = R.drawable.puzle_blanco
            emptyIndex = index
            updateGrid()
            checkIfSolved()
        } else {
            Toast.makeText(this, getString(R.string.moverAdyacentes), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isAdjacentToEmpty(index: Int): Boolean {
        val row = index / 4
        val col = index % 4
        val emptyRow = emptyIndex / 4
        val emptyCol = emptyIndex % 4

        return (row == emptyRow && Math.abs(col - emptyCol) == 1) ||
                (col == emptyCol && Math.abs(row - emptyRow) == 1)
    }

    private fun checkIfSolved() {
        for (i in 0 until 15) {
            val correctResourceId = resources.getIdentifier("puzle_${i + 1}", "drawable", packageName)
            if (puzzlePieces[i] != correctResourceId) return
        }
        finish()
    }

    // Verifica si el estado actual del puzzle es resolvible
    private fun isSolvable(pieces: Array<Int>): Boolean {
        var inversions = 0
        val pieceIds = pieces.filter { it != R.drawable.puzle_blanco }

        for (i in pieceIds.indices) {
            for (j in i + 1 until pieceIds.size) {
                if (pieceIds[i] > pieceIds[j]) inversions++
            }
        }

        val emptyRow = emptyIndex / 4
        val isEvenGrid = 4 % 2 == 0
        val isEvenInversions = inversions % 2 == 0

        return if (isEvenGrid) {
            (emptyRow % 2 == 0) == !isEvenInversions
        } else {
            isEvenInversions
        }
    }
}
