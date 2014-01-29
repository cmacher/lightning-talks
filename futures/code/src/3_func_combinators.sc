// http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import scala.language.postfixOps
type CoffeeBeans = String
type GroundCoffee = String
case class Water(temperature: Int)
type Milk = String
type FrothedMilk = String
type Espresso = String
type Cappuccino = String

def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
  println("start grinding...")
  Thread.sleep(Random.nextInt(2000))
  println("finished grinding...")
  s"ground coffee of $beans"
}

def heatWater(water: Water): Future[Water] = Future {
  println("heating the water now")
  Thread.sleep(Random.nextInt(2000))
  println("hot, it's hot!")
  water.copy(temperature = 85)
}

def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
  println("milk frothing system engaged!")
  Thread.sleep(Random.nextInt(2000))
  println("shutting down milk frothing system")
  s"frothed $milk"
}
def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
  println("happy brewing :)")
  Thread.sleep(Random.nextInt(2000))
  println("it's brewed!")
  "espresso"
}


def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

def temperatureOkay(water: Water): Future[Boolean] = Future {
  (80 to 85).contains(water.temperature)
}

// Map over the Future
val nestedFuture: Future[Future[Boolean]] = heatWater(Water(25)).map {
  water => temperatureOkay(water)
}



// FlatMap over the Future
val flatFuture: Future[Boolean] = heatWater(Water(25)).flatMap {
  water => temperatureOkay(water)
}


// we need to wait to see the future completed
//Await.result(flatFuture, 5 seconds)

val cappuccino = heatWater(Water(25)).flatMap(water =>
    grind("arabica beans")
      .flatMap(ground =>
        brew(ground, water))
          .flatMap(espresso =>
            frothMilk("milk")
              .map(foam =>
                combine(espresso, foam))))






Await.result(cappuccino, 10 seconds)























