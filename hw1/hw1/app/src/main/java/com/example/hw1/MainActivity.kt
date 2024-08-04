import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlin.random.Random
import com.example.hw1.R
import com.example.hw1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rollButton: Button
    private lateinit var playerTurnTextView: TextView
    private lateinit var problemTextView: TextView
    private lateinit var dieImageView: ImageView
    private var player1Points = 0
    private var player2Points = 0
    private var currentPlayer = 1
    private var jackpot = 5
    private var problemAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rollButton = findViewById(R.id.roll_button)
        playerTurnTextView = findViewById(R.id.player_turn_text_view)
        problemTextView = findViewById(R.id.problem_text_view)
        dieImageView = findViewById(R.id.die_image_view)

        rollButton.setOnClickListener { rollDie() }
        updateUI()
    }

    private fun updateUI() {
        playerTurnTextView.text = getString(if (currentPlayer == 1) R.string.player_1_turn else R.string.player_2_turn)
    }

    private fun rollDie() {
        val roll = (1..6).random()

        when (roll) {
            1 -> {
                dieImageView.setImageResource(R.drawable.dice1)
                generateAdditionProblem()
                problemTextView.text = problemAnswer.toString()
            }
            2 -> {
                dieImageView.setImageResource(R.drawable.dice2)
                generateSubtractionProblem()
                problemTextView.text = problemAnswer.toString()
            }
            3 -> {
                dieImageView.setImageResource(R.drawable.dice3)
                generateMultiplicationProblem()
                problemTextView.text = problemAnswer.toString()
            }
            4 -> Toast.makeText(this, "Roll Again for Double Points", Toast.LENGTH_SHORT).show()
            5 -> {
                Toast.makeText(this, "Lose a Turn", Toast.LENGTH_SHORT).show()
                switchPlayer()
            }
            6 -> Toast.makeText(this, "Try for Jackpot", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateAdditionProblem() {
        val num1 = (1..jackpot).random()
        val num2 = (1..jackpot).random()
        problemAnswer = num1 + num2
    }

    private fun generateSubtractionProblem() {
        val num1 = (1..jackpot).random()
        val num2 = (1..jackpot).random()
        problemAnswer = if (num1 > num2) num1 - num2 else num2 - num1
    }

    private fun generateMultiplicationProblem() {
        val num1 = (1..jackpot).random()
        val num2 = (1..jackpot).random()
        problemAnswer = num1 * num2
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == 1) 2 else 1
        updateUI()
    }

    private fun onTryForJackpot() {
        val input = InputNumberDialog.newInstance(this)
        input.show(supportFragmentManager, "input")

        // Set an onDismissListener to handle dismissal
        input.onDismissListener = {
            if (input.result == jackpot) {
                if (currentPlayer == 1) {
                    player1Points += jackpot
                } else {
                    player2Points += jackpot
                }
                jackpot += 5
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                switchPlayer()
            }
            updateUI()
        }
    }

class InputNumberDialog : DialogFragment() {
    var result = 0
    var onDismissListener: (() -> Unit)? = null
    companion object {
        fun newInstance(context: Context): InputNumberDialog {
            return InputNumberDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_input_number, container, false)
        val inputButton = view.findViewById<Button>(R.id.input_button)
        val inputEditText = view.findViewById<EditText>(R.id.input_edit_text)

        inputButton.setOnClickListener {
            result = inputEditText.text.toString().toInt()
            onDismissListener?.invoke()
            dismiss()
        }

        return view
    }
}}
