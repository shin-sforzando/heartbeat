# heartbeat

<!-- Badges -->

[![Last Commit](https://img.shields.io/github/last-commit/shin-sforzando/heartbeat)](https://github.com/shin-sforzando/heartbeat/graphs/commit-activity)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)

<!-- Synopsis -->

Application to share heartbeats observed by smartphone camera with others through visualization and vibration.

<!-- TOC -->

- [Prerequisites](#prerequisites)
- [How to](#how-to)
  - [Setup](#setup)
  - [Debug](#debug)
    - [Calva (VSCode Extension)](#calva-vscode-extension)
      - [Build and start the app, and connect Calva](#build-and-start-the-app-and-connect-calva)
      - [Start Expo](#start-expo)
    - [CLI](#cli)
      - [Using ClojureScript REPL](#using-clojurescript-repl)
      - [Command line CLJS REPL](#command-line-cljs-repl)
  - [Test](#test)
  - [Build](#build)
    - [Using EAS Build](#using-eas-build)
- [Misc](#misc)

## Prerequisites

(T. B. D.)

## How to

### Setup

1. `npm install`

### Debug

#### Calva (VSCode Extension)

Assuming you have installed the [Calva](https://calva.io) extension in VS Code:

##### Build and start the app, and connect Calva

1. Open the project in VS Code. Then:
1. Run the Calva command **Start a Project REPL and Connect (aka Jack-in)**
   1. Wait for shadow to build the project.

##### Start Expo

1. Then **Run Build Task**. This will start Expo and the Metro
   bundler. Wait for expo to show its menu options in the terminal pane.
1. In the expo menu press w for **open web**.

The app now should be running in your web browser and Calva automatically connects to it. Confirm this by evaluating something like this in Calva (from a cljs file or in the REPL window):

``` clojure
(js/alert "Hello world!")
```

You should see the alert pop up where the app is running.

Of course you should try to fire up the app on all simulators, emulators and phones you have as well. Please note that Calva will only be connected to one of your apps at a time, and it is a bit arbitrary which one. Use `(js/alert)` to check this.

#### CLI

```shell
$ npx shadow-cljs watch app
$ npm start
```

This will run Expo DevTools at <http://localhost:19002/>

To run the app in browser using expo-web (react-native-web), press `w` in the same terminal after expo devtools is started.
This should open the app automatically on your browser after the web version is built. If it does not open automatically, open <http://localhost:19006/> manually on your browser.

##### Using ClojureScript REPL

Once the app is deployed and opened in phone/simulator/emulator/browser, connect to nrepl and run the following:

```clojure
(shadow/nrepl-select :app)
```

NB: *Calva users don't need to do ^ this ^.*

To test the REPL connection:

```clojure
(js/alert "Hello from Repl")
```

##### Command line CLJS REPL

Shadow can start a CLJS repl for you, if you prefer to stay at the terminal prompt:

```shell
npx shadow-cljs cljs-repl :app
```

### Test

(T. B. D.)

### Build

A production build involves first asking shadow-cljs to build a release, then to ask Expo to work in Production Mode.

1. Kill the watch and expo tasks.
1. Execute `shadow-cljs release app`
1. Start the expo task (as per above)
   1. Enable Production mode.
   1. Start the app.

#### Using EAS Build

`expo build` is the classic way of building an Expo app, and `eas build` is the new version of `expo build`. Using EAS Build currently requires an Expo account with a paid plan subscription.

The steps below provide an example of using EAS Build to build an apk file to run on an Android emulator or device.

1. Install the latest EAS CLI by running `npm install -g eas-cli`
2. Log into your Expo account
3. Configure EAS Build in your project with `eas build:configure`.
4. Make your eas.json file contents look like this:

    ```json
    {
      "build": {
        "production": {},
        "development": {
          "distribution": "internal",
          "android": {
            "buildType": "apk"
          },
          "ios": {
            "simulator": true
          }
        }
      }
    }
    ```

5. Commit your changes, run `eas build --profile development`, and follow the prompts.
6. Navigate to the URL given by the command to monitor the build. When it completes, download the apk and install it on your device or emulator.

See [the EAS Build docs](https://docs.expo.dev/build/introduction/) for more information.

## Misc

This repository is [Commitizen](https://commitizen.github.io/cz-cli/) friendly, following [GitHub flow](https://docs.github.com/en/get-started/quickstart/github-flow).
