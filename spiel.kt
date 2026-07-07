import de.th_koeln.basicstage.*
import de.th_koeln.basicstage.geoevents.*
import de.th_koeln.basicstage.motion.*
import de.th_koeln.imageprovider.Assets
import de.th_koeln.basicstage.controller.KeyboardMotionControl
import kotlin.random.Random
fun main() {
    val stage = Stage()
    val scoreDisplay = Actor()
    scoreDisplay.x = 50
    scoreDisplay.y = 50
    stage.addActor(scoreDisplay)

    val player = Player(stage)


    player.play(stage) {
        scoreDisplay.text.content = player.score.toString()
        if(player.score < 0) {
            endGame(stage,player.score,player)

        }
    }
}



fun endGame(s: Stage, finalScore: Int,player:Player) {
    val gameOver = DisplayActor("Game Over! Score: $finalScore")

    gameOver.useStarIcon()
    gameOver.x = 250
    gameOver.y = 250
    s.addActor(gameOver)
    s.removeActor(player.p)
}


fun createGegner(p: Actor, s: Stage, player: Player, onScoreChange: () -> Unit): Gegner {
    val gegner = Gegner()

    p.addIntersectionEventListener(
        gegner.actor,
        object : IntersectionEventListener {
            override fun onStartIntersection() {
                gegner.actor.opacity = 0
                s.removeActor(gegner.actor)
                player.score += gegner.scoreChange  // ← -30
                onScoreChange()                     // ← Display aktualisieren
            }

            override fun onStopIntersection() {
                gegner.actor.visible = true
            }
        }
    )

    gegner.actor.x = Random.nextInt(800)
    gegner.actor.y = 0
    gegner.drop()
    return gegner
}

fun createFreund(p: Actor, s: Stage, player: Player, onScoreChange: () -> Unit): Freund {
    val freund = Freund()

    p.addIntersectionEventListener(
        freund.actor,
        object : IntersectionEventListener {
            override fun onStartIntersection() {
                freund.actor.opacity = 0
                s.removeActor(freund.actor)
                player.score += freund.scoreChange
                onScoreChange()
            }

            override fun onStopIntersection() {
                freund.actor.visible = true
            }
        }
    )
    freund.actor.x = Random.nextInt(800)
    freund.actor.y = 0
    freund.drop()
    return freund
}



abstract class Person {
    open val actor = Actor(Assets.KODEE, 20, 20, 50, 50)
    open var scoreChange = 0

    open fun drop() {
        actor.motion.direction = Direction.SOUTH.orientation
        actor.motion.speed = 1
    }
}

class Gegner : Person() {
    override val actor: Actor = Actor(Assets.snacks.RAMEN)
    override var scoreChange = -30
}

class Freund : Person() {
    override val actor: Actor = Actor(Assets.snacks.PIZZA)
    override var scoreChange = 30
}

class Player(s: Stage) {


    val p = Actor(Assets.misc.PACMAN)
    var score = 0


    fun play(s: Stage, onScoreChange: () -> Unit) {

        p.motion.keyMotionControl = KeyboardMotionControl.MOTION_BY_ARROWS_CONTINUE
        s.addActor(p)
        p.animation.everyNsteps.timeSpan = 100
        p.animation.everyNsteps.reactionForTimePassed = {
            val a = createGegner(p, s, this, onScoreChange)
            val b = createFreund(p, s, this, onScoreChange)
            s.addActor(a.actor)
            s.addActor(b.actor)
        }




    }
}
