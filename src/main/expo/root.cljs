(ns expo.root
  (:require
   ["expo" :as expo]
   ["create-react-class" :as crc]))

(defonce root-ref (atom nil))
(defonce root-component-ref (atom nil))

(defn handle-component-did-mount []
  (this-as this
    (reset! root-component-ref this)))

(defn handle-component-will-unmount []
  (reset! root-component-ref nil))

(defn render-body []
  (let [body @root-ref]
    (if (fn? body)
      (body)
      body)))

(defn create-root-component []
  (crc
   #js {:componentDidMount handle-component-did-mount
        :componentWillUnmount handle-component-will-unmount
        :render render-body}))

(defn render-root [root]
  (try
    (let [first-call? (nil? @root-ref)]
      (reset! root-ref root)

      (if-not first-call?
        (when-let [root @root-component-ref]
          (.forceUpdate ^js root))
        (let [Root (create-root-component)]
          (expo/registerRootComponent Root))))
    (catch js/Error e
      (js/console.error "Error rendering root component:" e))))
