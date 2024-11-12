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

(defn first_st [stack]
  (if (empty? stack) 0 (first stack))
)

(defn second_st [stack]
  (if (empty? (rest stack)) 0 (second stack))
)

(defn op_aritm [stack op]
  (let [valor1 (second_st stack)
        valor2 (first_st stack)
        resultado (int (op valor1 valor2))]
  (conj (drop 2 stack) resultado)
  )
)


(defn -main [ruta]
  (loop [toroide (parsear_archivo ruta)
         pc {:fil 0 :col 0 :dir RIGHT}
         stack '()
         str_mode false]
    (let [ch (get-in toroide [(pc :fil) (pc :col)])]
    (cond
      (= ch \") (recur toroide (inc_pc pc) stack (not str_mode))
      str_mode (recur toroide (inc_pc pc) (conj stack (ascii ch)) str_mode)
      (es_numero? ch) (recur toroide (inc_pc pc) (conj stack (num_ch_a_int ch)) str_mode)
      :instr (case ch
               \+ (recur toroide (inc_pc pc) (op_aritm stack +) str_mode)
               \- (recur toroide (inc_pc pc) (op_aritm stack -) str_mode)
               \* (recur toroide (inc_pc pc) (op_aritm stack *) str_mode)
               \/ (recur toroide (inc_pc pc) (op_aritm stack /) str_mode)
               \% (recur toroide (inc_pc pc) (op_aritm stack rem) str_mode)
               \! (recur toroide (inc_pc pc) (conj (rest stack) (if (zero? (first_st stack)) 1 0)) str_mode)
               \` (recur toroide (inc_pc pc) (conj (drop 2 stack) (if (> (second_st stack) (first_st stack)) 1 0)) str_mode)
               \> (recur toroide (inc_pc (assoc pc :dir RIGHT)) stack str_mode)
               \< (recur toroide (inc_pc (assoc pc :dir LEFT)) stack str_mode)
               \^ (recur toroide (inc_pc (assoc pc :dir UP)) stack str_mode)
               \v (recur toroide (inc_pc (assoc pc :dir DOWN)) stack str_mode)
               \? (recur toroide (inc_pc (assoc pc :dir (rand-nth [UP, DOWN, LEFT, RIGHT]))) stack str_mode)
               \_ (recur toroide (inc_pc (assoc pc :dir (if (zero? (first_st stack)) RIGHT LEFT))) (rest stack) str_mode)
               \| (recur toroide (inc_pc (assoc pc :dir (if (zero? (first_st stack)) DOWN UP))) (rest stack) str_mode)
               \: (recur toroide (inc_pc pc) (if (empty? stack) '(0 0) (conj stack (first_st stack))) str_mode)
               \\ (recur toroide (inc_pc pc) (conj (drop 2 stack) (first_st stack) (second_st stack)) str_mode)
               \$ (recur toroide (inc_pc pc) (rest stack) str_mode)
               \. (do (print (first_st stack)) (recur toroide (inc_pc pc) (rest stack) str_mode))
               \, (do (print (char (first_st stack))) (recur toroide (inc_pc pc) (rest stack) str_mode))
               \# (recur toroide (inc_pc (inc_pc pc)) stack str_mode)
               \g
               \p
               \&
               \~
               \space (recur toroide (inc_pc pc) stack str_mode)
               \@ (println stack)
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