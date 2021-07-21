# cargo

Tool for planning truck loading by arranging blocks.

## Overview

Requirements and features:

- Set dimensions of loading area and cargo in cm.
- Label and categorise cargo.
- Cargo can't overlap.
- Multiple instances of cargo with the same dimensions can be created.
- Rotation
- Optionally: Calculate optimal arrangement.

Typical cargo area size: 7.2m x 2.44m (space for 18 Euro pallets). Common large cargo area size: 13.6m x 2.44.

## Development

To get an interactive development environment run:

    lein fig:build

This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein clean
    lein fig:min

Use CIDER with:

    cider-jack-in-cljs (C-c M-J)

Two prompts will show up. Use the following options:

    ClojureScript REPL type: figwheel-main
    Main build: :dev
