(ns tp2-befunge93.core)

(def MAX_FILAS 25)
(def MAX_COLUMNAS 80)
(def UP 'up)
(def DOWN 'down)
(def LEFT 'left)
(def RIGHT 'right)

(defn es_numero? [x] (contains? #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9} x))
(defn ascii [x] (int x))
(defn num_ch_a_int [x] (- (ascii x) 48))

(defn crear_matriz []
   (vec (repeat 25 (vec (repeat 80 \space))))
)

(defn rmpl_elem [matriz fil col elem]
  (assoc matriz fil (assoc (matriz fil) col elem))
)

(defn parsear_archivo [ruta]
  (loop [matriz (crear_matriz)
        fil 0
        col 0
        chs (map char (slurp ruta))]
    (cond
      (= chs '()) matriz
      (= (first chs) \newline) (recur matriz (inc fil) 0 (rest chs))
      (= col 80) (recur matriz (inc fil) 0 chs)
      true (recur (rmpl_elem matriz fil col (first chs)) fil (inc col) (rest chs))
    )
  )
)

(defn inc_pc [pc]
  (condp = (get pc :dir)
    UP (if (zero? (pc :fil)) (assoc pc :fil (dec MAX_FILAS)) (update pc :fil dec))
    DOWN (if (= (pc :fil) (dec MAX_FILAS)) (assoc pc :fil 0) (update pc :fil inc))
    LEFT (if (zero? (pc :col)) (assoc pc :col (dec MAX_COLUMNAS)) (update pc :col dec))
    RIGHT (if (= (pc :col) (dec MAX_COLUMNAS)) (assoc pc :col 0) (update pc :col inc))
    )
)

;(defn operacion [stack op]
;
;)


(defn -main [ruta]
  (loop [toroide (parsear_archivo ruta)
         pc {:fil 0 :col 0 :dir RIGHT}
         stack '()
         str_mode false]
    (let [ch (get-in toroide [(pc :fil) (pc :col)])]
    (cond
      (= ch \") (recur toroide (inc_pc pc) stack (not str_mode))
      str_mode (recur toroide (inc_pc pc) (conj stack (ascii ch)) true)
      (es_numero? ch) (recur toroide (inc_pc pc) (conj stack (num_ch_a_int ch)) false)
      :instr (case ch
               \+ 
               \-
               \*
               \/
               \%
               \!
               \`
               \>
               \<
               \^
               \v
               \?
               \_
               \|
               \"
               \:
               \\
               \$
               \.
               \,
               \#
               \g
               \p
               \&
               \~
               \@ (println "fin")
               :else (println "Error: instruccion desconocida"))
    )
    )
  )
)

;pc como lista '(fil col dir), despues lo tomo con apply
;o como diccionario {:fil ,,, :col ,,, :dir ,,,}


;(map str (slurp "src/tp2_befunge93/hello.bf"))
;(mapv char (slurp "src/tp2_befunge93/hello.bf"))

(defn inc_col [col]
  (rem (inc col) 80)
  )

(defn inc_fil [fil]
  (rem (inc fil) 25)
  )

(defn fact
  ([n] (fact n 1))
  ([n resultado] (if (zero? n) resultado
                               (recur (dec n) (* resultado n)))))

;(loop [stack '()]
;  (do
;    (if (some-condition? stack)
;      stack
;      (let [stack (conj stack 1)]))
;    (recur stack)
;  )
;)

;(defn prueba [a b c]
;  (if (zero? c)
;    a
;    (recur [a b 0]))
;)