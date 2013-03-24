# flightgear

A flightgear library for the London Clojure Dojo.

## Usage

# FlightGear

1) Download FlightGear for your platform from http://www.flightgear.org/download/

2) Run with the command line parameter "--telnet=5401"

# Leinigen

Add to your project.clj:

```clojure
  :dependencies [[org.clojars.dalethatcher/flightgear "0.1.0-SNAPSHOT"]]
```

## API

# Quick Start

```clojure
> (use 'flightgear.api)
> (connect "localhost" 5401)
> (starter! true) ; wait until engine started, 'engine-running?' can be used to query
> (starter! false)
> (flaps! 0.5)
> (throttle! 1)  ; wait for a little while and you should be airbourne
> (rudder! 0.1) ; steer a bit to the right (single props tend to veer to one side)
```

You'll briefly have the elation of flying, most likely followed by a nice crash.

# Instruments

Remember these values will have lag and some inaccuracy, depending on local conditions.

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

## License

Copyright © 2013 Dale Thatcher

Distributed under the Eclipse Public License, the same as Clojure.
