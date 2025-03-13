(ns example.app
  (:require [example.events]
            [example.subs]
            [example.widgets :refer [button]]
            [expo.root :as expo-root]
            ["expo-status-bar" :refer [StatusBar]]
            [re-frame.core :as rf]
            ["react-native" :as rn]
            [reagent.core :as r]
            ["@react-navigation/native" :as rnn]
            ["@react-navigation/native-stack" :as rnn-stack]))

(defonce shadow-splash (js/require "../assets/shadow-cljs.png"))
(defonce cljs-splash (js/require "../assets/cljs.png"))

(defonce Stack (rnn-stack/createNativeStackNavigator))

(def styles
  {:container {:flex 1
               :padding-vertical 50
               :justify-content :space-between
               :align-items :center
               :background-color :white}
   :centered-view {:align-items :center}
   :bold-text {:font-weight :bold
               :font-size 72
               :color :blue
               :margin-bottom 20}
   :small-text {:font-weight :normal
                :font-size 15
                :color :blue}
   :image {:width 160
           :height 160}
   :row {:flex-direction :row
         :align-items :center
         :margin-bottom 20}})

(defn home [^js props]
  (r/with-let [counter (rf/subscribe [:get-counter])
               tap-enabled? (rf/subscribe [:counter-tappable?])]
    [:> rn/View {:style (:container styles)}
     [:> rn/View {:style (:centered-view styles)}
      [:> rn/Text {:style (:bold-text styles)} @counter]
      [button {:on-press #(rf/dispatch [:inc-counter])
               :disabled? (not @tap-enabled?)
               :style {:background-color :blue}}
       "Tap me, I'll count"]]
     [:> rn/View {:style (:centered-view styles)}
      [button {:on-press (fn []
                           (-> props .-navigation (.navigate "About")))}
       "Tap me, I'll navigate"]]
     [:> rn/View
      [:> rn/View {:style (:row styles)}
       [:> rn/Image {:style (:image styles)
                     :source cljs-splash}]
       [:> rn/Image {:style (:image styles)
                     :source shadow-splash}]]
      [:> rn/Text {:style (:small-text styles)}
       "Using: shadow-cljs+expo+reagent+re-frame"]]
     [:> StatusBar {:style "auto"}]]))

(defn about []
  (r/with-let [counter (rf/subscribe [:get-counter])]
    [:> rn/View {:style (:container styles)}
     [:> rn/View {:style (:centered-view styles)}
      [:> rn/Text {:style (:bold-text styles)}
       "About heartbeat"]
      [:> rn/Text {:style (:bold-text styles)}
       (str "Counter is at: " @counter)]
      [:> rn/Text {:style (:small-text styles)}
       "Built with React Native, Expo, Reagent, re-frame, and React Navigation"]]
     [:> StatusBar {:style "auto"}]]))

(defn root []
  (r/with-let [!root-state (rf/subscribe [:navigation/root-state])
               save-root-state! (fn [^js state]
                                  (rf/dispatch [:navigation/set-root-state state]))
               add-listener! (fn [^js navigation-ref]
                               (when navigation-ref
                                 (.addListener navigation-ref "state" save-root-state!)))]
    [:> rnn/NavigationContainer {:ref add-listener!
                                 :initialState (when @!root-state (-> @!root-state .-data .-state))}
     [:> Stack.Navigator
      [:> Stack.Screen {:name "Home"
                        :component (fn [props] (r/as-element [home props]))
                        :options {:title "heartbeat"}}]
      [:> Stack.Screen {:name "About"
                        :component (fn [props] (r/as-element [about props]))
                        :options {:title "About"}}]]]))

(defn start
  {:dev/after-load true}
  []
  (expo-root/render-root (r/as-element [root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
