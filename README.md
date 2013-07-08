# flightgear

A flightgear library originally written for the London Clojure Dojo,
now being used to turn people on to Clojure at Clojure Dojos the world
over.

## Usage

# FlightGear

1) Download FlightGear for your platform from
http://www.flightgear.org/download/ If you're at a meetup group and
wifi is saturated, go the BitTorrent route. The mirrors are good, but
sometimes timeout. In general, it's a good idea to have a USB stick
handy to pass around in case the wifi situation is really bad.

2) Run with the parameter `--telnet=5401`. Assuming you've downloaded
the binary, the way you add this option is by opening the FlightGear
GUI application, clicking `Advanced Features`, then under the `Others`
tab enter `--telnet=5401`. Once you've done that, you can click `Start
Flight`, and you're off to the races.

# Leinigen

Add to your project.clj:

```clojure
  :dependencies [[org.clojars.dalethatcher/flightgear "0.1.0-SNAPSHOT"]]
```

## API

# Quick Start

```clojure
> (use 'flightgear.api)
> (connect "localhost" 5401) ;; => true
> (starter! true) ;; wait until engine started, 'engine-running?' can be used to query
> (starter! false)
> (flaps! 0.5)
> (throttle! 1)  ;; wait for a little while and you should be airbourne
> (rudder! 0.1) ;; steer a bit to the right (single props tend to veer to one side)
```

You'll briefly have the elation of flying, most likely followed by a
nice crash.

# Instruments

Remember these values will have lag and some inaccuracy, depending on
local conditions. Note that the inaccuracy is normal: These are analog
instruments.

```clojure
> (indicated-airspeed-kt)
76.08771873
> (indicated-altitude-ft)
2271.735558
> (indicated-attitude)
{:indicated-roll-deg -26.59438056, :indicated-pitch-deg 1.811541719}
> (indicated-heading-deg)
110.7191063
> (engine-running?)
true
```

If you're wondering whether or not you're in a sane starting gamestate
after a reset and/or crash, you can run the following from the REPL
(assuming you've successfully connected) to peek at the values after
doing a `File > Reset`:

```clojure
(println {:airspeed (indicated-airspeed-kt)
          :altitude (indicated-altitude-ft)
          :attitude (indicated-attitude)
          :position (position)
          :orientation (orientation)
          :heading (indicated-heading-deg)})
```

My starting values with a "Cessna 172P - Canvas Demo" aircraft from
San Francisco International airport are as follows:

```clojure
{:airspeed 2.136604309,
 :altitude -41.86214963,
 :attitude {:indicated-roll-deg 40,
            :indicated-pitch-deg 12},
 :position {:sea-level-radius-ft 2.089963105E7,
            :ground-elev-ft 1.591905672,
 :ground-elev-m 0.4852128489,
 :altitude-ft 6.006853687,
 :latitude-deg 37.62870959,
 :longitude-deg -122.3933424},
 :orientation {:roll-deg -0.2522372877,
               :heading-deg 117.8880022,
               :pitch-deg 2.786319015},
 :heading 108.2690857}
```

# Controls

See http://www.aerospaceweb.org/question/design/q0101.shtml for a description of aircraft control surfaces.

```clojure
(starter! true|false) ; Turn the starter key, don't forget to turn off!
(throttle! 0..1)      ; Power to the engine.
(flaps! 0..1)         ; Sort of a fixed up and down mostly used for take off and landing.
(rudder! -1..1)       ; Steer left and right (yaw).
(elevator! -1..1)     ; Up and down (pitch).
(aileron! -1..1)      ; Left and right with rotate (roll).
```

# Telemetry

These are direct values from the flight simulator, aka cheating! :)

```clojure
> (position)
{:ground-elev-ft 1.602206714, :ground-elev-m 0.4883526066, :altitude-ft 6.017082775, :latitude-deg 37.62871089, :longitude-deg -122.3933408}
> (orientation)
{:roll-deg -0.2482645472, :heading-deg 117.8881856, :pitch-deg 2.792487719}
> (velocities)
{:wBody-fps 1.462199396E-8, :vBody-fps 1.285159097E-10, :uBody-fps -2.054781393E-9}
```
# Resetting Your Plane and Other Tips

You're going to crash. It's only a matter of time. When you do, there
can be some issues getting the game state, repl, and your
instrumentation back to where it ought to be. With the FlightGear
window (the one you just saw your plane crash in) focused, click `File > Reset`
to get back to where you started.

You don't have to "cheat" if you don't want to, but under `Advanced
Features > Features`, I suggest turning off `Real weather fetch`,
`Horizon effect`, `Clouds`, and `3D Clouds`. It'll make things run
smoother, and the last thing you need on your maiden voyage is
annoying doses of reality. This is programming. You have the option of
turning off *physics*. Take advantage.

Is it really dark where you're flying? Click `Environment > Time
Settings` and change it to morning if that's easier for everyone to
see.

Finally, there are lots of reasons why things behave the way they do
in FlightGear. It tries to be a first-class simulator. There is
documentation worth reading:
[http://www.flightgear.org/Docs/getstart/getstart.html#getstartpa1.html](FlightGear Documentation)

## License

Copyright © 2013 Dale Thatcher

Distributed under the Eclipse Public License, the same as Clojure.
