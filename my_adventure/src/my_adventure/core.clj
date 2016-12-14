(ns my-adventure.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str])
  (:gen-class))

(def the-map
  { :entrance     {:desc "In front of you stands the castle of Durham. Its walls are charred with the dragon’s flame breath, and you can still see the distinct claw marks of the beast had left behind. Somehow you feel like from this point on there is no return. It is your duty to save the kingdom! Venture forward brave adventurer."
                   :title "at the Entrance"
                   :dir {
                         :north :courtyard}
                   :contents []}

    :courtyard    {:desc "You’ve crossed drawbridge into the courtyard. The once lush grass is not blackened into soot, and down the path stands the massive door to the ransacked castle."
                   :title "in the Courtyard"
                   :dir {
                         :north :foyer
                         :south :entrance}
                   :contents [:raw_egg {:desc "You have picked up a raw egg."}]}

    :foyer        {:desc "I moved past the courtyard, and finally came into the foyer. I can see the damage the dragon did as he forced his way into the castle. He seemed to have left behind a few of his scales by accident, and I pick one up and notice that it's harder than any armor I have ever seen. It is clear that the mighty beast’s size was too great for this dwelling of man. Bálormr must be at least twenty feet tall! I will need to find a suitable weapon to defeat the beast."
                   :title "in the Foyer"
                   :dir {
                         :west  :servantQuarter
                         :east  :magetower
                         :north :greatHall
                         :south :courtyard}
                   :contents []}

    :greatHall    {:desc "As I finally I stepped into the famed Hall of the Durham Castle, my mind drift back to my childhood briefly, to the tales I heard of this place. Yet, instead of a chamber lit by a thousand candles, I only found the destruction left in Bálormr’s wake. Instead of a bountiful feast fit for one of the most powerful King in the realm, I can only hear the hiss of the wind blowing through hole Bálormr tore through the ceiling. Ahead of me, I can see the king’s throne, further up,  I see the entrance to the inner keep."
                   :title "in the Great Hall"
                   :dir {
                         :north :innerKeep
                         :south :foyer
                         :east :throneroom}
                   :contents []}

    :innerKeep    {:desc "The the room looks like it has been scorched and torn apart. The dragon must have been through here! I must be getting closer. Better keep an eye out."
                    :title "in the Inner Keep"
                    :dir {
                          :north :keepHallway
                          :south :greatHall
                          :east  :potionChamber
                          :west  :servantQuarter}
                    :contents []}

    :keepHallway   {:desc "I hear my steps echo through the hallway as I walk to the other end. It feels so ominous in the dark, a shiver runs down my spine. However, I keep moving, not letting my guard down."
                    :title "in the Keep Hallway"
                    :dir {
                          :north :keeproom1 ;;want this to be blocked at the moment
                          :south :innerKeep}

                    :contents []}

    :keeproom1   {:desc "As I step inside, I smell the burning of wood. There are glowing embers along the walls; the dragon can’t be much further now"
                  :title "in the first Keep Room"
                  :dir {
                        :north :keeproom2
                        :south :keepHallway}
                  :contents []}

    :keeproom2   {:desc "I start feeling the heat now, sweat beading down my face. I start to grow anxious as I continue forward, unsure of what lies ahead"
                  :title "in the second Keep Room"
                  :dir {
                        :north :keeproom3
                        :south :keeproom2}
                  :contents []}

    :keeproom3   {:desc "The heat in this room is sweltering, the flickering of flames eating away at the walls and floorboards. I carefully venture forward, but then suddenly, the floor behind me falls out! There is no way back now, only forward. I gulp and prepare myself for the worst, the dragon is now ahead."
                  :title "in the third Keep Room"
                  :dir {
                        :north :bossroom}

                  :contents []}

    :bossroom   {:desc "Through the final door, I step into a large chamber, the heat emanating from it is so strong. In front of me lies the gigantic dragon, his powerful body standing in front of me. His armored scales glisten in the light of the flames, and his eyes stare down at me. Without wasting any time, I charge to the dragon, giving it my all!"
                  :title "in the Final Battle"
                  :dir {}

                  :contents []}

    :kitchen    {:desc "I seemed to have walked into the kitchen. There is broken plates, bowls, and silverware littering the floor, and also different food splattered everywhere. An awful stench lingers in the room, and I cover my nose."
                  :title "in the Kitchen"
                  :dir {
                        :north :servantQuarter
                        :east  :foyer
                        :south :innerHall}

                  :contents []}

    :servantQuarter    {:desc "This must be the servant’s quarters. I see belongings scattered everywhere, and cases of clothing that had been attempted to be filled laid abandoned. The foul stench of blood and death was easily noticeable. I’d rather not stay here too long."
                        :title "in the Servant's Quarter"
                        :dir {
                              :east  :innerKeep
                              :south :kitchen}

                        :contents []}

    :dungeon    {:desc "The room is dimly lit and large shadows of dangerous torture instruments loom over the room. Fresh blood is splattered over the walls, and there is a blue, ripped apart robe lying in heap."
                 :title "in the Dungeon"
                 :dir {
                       :east  :keeproom3 ;;keep hidden somehow
                       :south :cell9}

                 :contents [:ring]}

    :innerHall  {:desc "I enter into the hall only to see the statues of knights knocked over, the only light source present coming from the stained large glass windows. My steps echo throughout the room as I explore further."
                 :title "in the Inner Hall"
                 :dir {
                       :north  :kitchen
                       :east   :storageRoom
                       :west  :toolRoom
                       :south :armory}

                 :contents []}

    :storageRoom  {:desc "I walk into the room and see row upon rows of empty shelves that had once held food. It seems rather empty inside."
                   :title "in the Storage Room"
                   :dir {
                         :west  :innerHall}

                   :contents []}

    :armory  {:desc "With just one glance, I notice that this must have been the armory. Broken weapons and armor lay in the rubble. Maybe there is something useful I can find here..."
                :title "in the Armory"
                :dir {
                      :north :innerHall}

                :contents [:shield]}

    :toolRoom  {:desc "An assortment of different tools are scattered over the room as I look around. Shovels, rakes, and plenty of other things."
                :title "in the Tool Room"
                :dir {
                      :east :innerHall
                      :north :cell1}

                :contents []}

    :cell1  {:desc "Scanning over the contents of the cell, I don’t notice anything out of place. Just an empty cell."
                :title "in Cell One"
                :dir {
                      :south :toolRoom
                      :north :cell2}

                :contents []}

    :cell2  {:desc "Looking around, this cell also seems to be empty."
                :title "in Cell Two"
                :dir {
                      :south :cell1
                      :north :cell3}

                :contents []}

    :cell3  {:desc "Another empty cell."
                :title "in Cell Three"
                :dir {
                      :south :cell2
                      :north :cell4}

                :contents []}

    :cell4  {:desc "Another empty cell."
                :title "in Cell Four"
                :dir {
                      :south :cell3
                      :north :cell5}

                :contents []}

    :cell5  {:desc "Another empty cell."
                :title "in Cell Five"
                :dir {
                      :south :cell4
                      :north :cell6}

                :contents []}

    :cell6  {:desc "Another empty cell."
                :title "in Cell Six"
                :dir {
                      :south :cell5
                      :north :cell7}

                :contents []}

    :cell7  {:desc "Another empty cell."
                :title "in Cell Seven"
                :dir {
                      :south :cell6
                      :north :cell8}

                :contents []}

    :cell8  {:desc "Another empty cell."
                :title "in Cell Eight"
                :dir {
                      :south :cell7
                      :north :cell9}

                :contents []}

    :cell9  {:desc "Another empty cell...oh wait. There is something different about this cell. In the back, there is heavily armored door that seems to have been busted open. I wonder what could be there."
                :title "in Cell Nine"
                :dir {
                      :south :cell8
                      :north :dungeon}

                :contents [:club]}

    :magetower  {:desc "I entered a brightly lit room. On the far side of the room, there is an impressive oak desk. The owner of the room, must have been somebody powerful. I scanned across the room, I saw all sort of strange contraption, watches, skulls, and even skin of some unrecognizable beast. There are piles of old scrolls spread across the floor. I picked up one, and saw the drawing of a magician casting spelling at a dragon, and forcing the beast to the ground. Could the king’s magician been working on something to defeat the dragon?"
                 :title "in the Mage's Tower"
                 :dir {
                       :west :foyer
                       :north :potionchamber}

                 :contents []}

    :potionchamber  {:desc "The room is dimly lit, and I am surrounded by jars and tubes on all sides, filled with things that I have never seen before: eyes of newt, a lizard’s tail, and horn of some strange animal. In the center of the room, there are flask containing liquids of many different colors, some clear and others murky.."
                     :title "in the Potion Chamber"
                     :dir {
                           :south :magetower
                           :west :innerKeep
                           :north :magesquarter}

                     :contents []}

    :magesquarter  {:desc "Another empty cell.This must be where the mage came to rest. Much of the room remained undisturbed. After seeing the damages outside, It is hard to imagine how the men lived before Bálormr attacked. I looked around, and saw a wonderfully decorated stone door, with a series of strange symbols above. I walked closer and l saw more clearly that the symbols are words, they read, “Greet me friend, and you shall be open to knowledge that you can only dream of.”"
                    :title "in the Mage's Quarters"
                    :dir {
                          :south :potionchamber
                          :north :library}

                    :contents []}

    :library  {:desc "Haha, it is fitting for the magicians to have such strange wit. I see that I am in the library. The walls are filled with all sorts of books. They must be worth a fortune. Will there be something that I can use against the dragon?"
                  :title "in the Library"
                  :dir {
                        :south :magesquarter}

                  :contents [:spellbook]}

    :throneroom  {:desc "I stepped out of the darkness and into a magnificent room, filled with gilded walls and chandeliers. This can only be the throne room of the King."
                     :title "in the Throne Room"
                     :dir {
                           :west :greatHall
                           :south :royalchamber}

                     :contents []}

    :royalchamber  {:desc "I entered the Royal Chamber of the King. I stopped briefly to admire the plush bedding. Wherever he is know, he lived a good life."
                      :title "in the Royal Chamber"
                      :dir {
                            :north :thronechamber
                            :south :kingsstudy}

                      :contents []}

    :kingsstudy  {:desc "I pushed further ahead, I arrived into the King’s study. In front of me are his book and desk made of fine ebony wood. On the wall I can see the map of Durham laid out in front of me."
                      :title "in the King's Study"
                      :dir {
                            :north :royalchamber
                            :south :treasurechamber}

                      :contents []} ;;puzzle to find treasure chamber

    :treasurechamber  {:desc "I pushed further ahead, I arrived into the King’s study. In front of me are his book and desk made of fine ebony wood. On the wall I can see the map of Durham laid out in front of me."
                          :title "in the Treasure Chamber"
                          :dir {
                                :north :kingsstudy
                                :east :keeproom1}

                          :contents [:sword]}})



    ;;:grue-pen {:desc "It is very dark. You are about to be eaten by a grue."
    ;;              :title "in the grue pen"
    ;;              :dir {:south :courtyard}
    ;;              :contents []})


(def adventurer
  { :location :cell9
    :inventory #{}
    :seen #{}
    :done true})

(defn status [player]
  (let [location (player :location)]
    (println (str "You are " (-> the-map location :title) "."))
    (when-not ((player :seen) location)
      (println (-> the-map location :desc)))
    (println "---------------------------------")
    (update-in player [:seen] #(conj % location))))

(defn search [player]
  (let [location (player :location)]
    (cond
      (= location :armory)
         (do (if (not (contains? (player :inventory) :shield))
               (do
                 (println "On the walls, I see some random pieces of equipment left behind by the soldiers. Most of them seem too cumbersome, so I pick up a shield that is still in good condition.")
                 (println "---------------------------------")
                 (-> player (assoc-in [:location] location)
                            (update-in [:inventory] #(conj % :shield))))
               (do
                 (println "I have found nothing interesting.")
                 (println "---------------------------------")
                 player)))
      (= location :cell9)
         (do (if (not (contains? (player :inventory) :club))
               (do
                 (println "I search the back corner of the room and notice that there is actually a club, and I pick it up. Looks like I could break through something if I ever needed to.")
                 (println "---------------------------------")
                 (-> player (assoc-in [:location] location)
                            (update-in [:inventory] #(conj % :club))))
               (do
                 (println "I have found nothing interesting.")
                 (println "---------------------------------")
                 player)))
      (= location :library)
         (do (if (not (contains? (player :inventory) :spellbook))
               (do
                 (println "I sift through the bookshelves, and I come across a beautifully bounded leather book. I take the book and open it to where there was a bookmark. There is spell named Dragonrend, and reading its description, the spell will force the dragon to the ground and immobilize it. I pour over it endlessly to memorize the words, and then I stuff the book with my other belongings.")
                 (println "---------------------------------")
                 (-> player (assoc-in [:location] location)
                            (update-in [:inventory] #(conj % :spellbook))))
               (do
                 (println "I have found nothing interesting.")
                 (println "---------------------------------")
                 player)))
      (= location :treasurechamber)
         (do (if (not (contains? (player :inventory) :sword))
               (do
                 (println "I grab the sword and whirl it around. This will surely bring the dragon's demise.")
                 (println "---------------------------------")
                 (-> player (assoc-in [:location] location)
                            (update-in [:inventory] #(conj % :sword))))
               (do
                 (println "I have found nothing interesting.")
                 (println "---------------------------------")
                 player)))
      (= location :dungeon)
         (do (if (not (contains? (player :inventory) :ring))
               (do
                 (println "I search through the robs and come across a magic ring! I am not sure what it does, but it might come in handy so I take it anyways.")
                 (println "---------------------------------")
                 (-> player (assoc-in [:location] location)
                            (update-in [:inventory] #(conj % :ring))))
               (do
                 (println "I have found nothing interesting.")
                 (println "---------------------------------")
                 player)))
      :else
         (do
            (println "I have found nothing interesting.")
            (println "---------------------------------")
            player))))

(defn to-keywords [commands]
  ;; should be a list of strings
  (mapv keyword (str/split commands #"[.,?! ]+")))

(defn go [dir player]
  (let [location (player :location)
        dest (->> the-map location :dir dir)]
      (if (nil? dest)
          (do (println "You can't go that way.")
              (println "---------------------------------")
              player)
       (assoc-in player [:location] dest))))

;;(defn search [player])
  ;;If there is an item in the room, print the item's description.
  ;;Move the item into the player's inventory.
  ;;Remove the item from the room.
  ;;If there is no item, print "There is nothing to be found."

(defn help [player]
  (println "Available commands:")
  (println "north: make the player go north.")
  (println "east: make the player go east.")
  (println "south: make the player go south.")
  (println "west: make the player go west.")
  (println "search: searches the current location.")
  (println "help: shows the help menu.")
  (println "---------------------------------")
  (let [location (player :location)]
    (assoc-in player [:location] location)))

  ;;Print out possible commands based on the player's location.

(defn respond [player command]
  (println "---------------------------------")
  (match command
                [:look] (update-in player [:seen] #(disj % (-> player :location)))
                [:search] (search player)
                [:help] (help player)
                [:north] (go :north player)
                [:south] (go :south player)
                [:east] (go :east player)
                [:west] (go :west player)
                _ (do (println "I don't understand you.")
                      (println "---------------------------------")
                      player)))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (println "It has been 1000 moons since the land of Durham has been shrouded under the wings of Bálormr Flame-Shroud The Repugnant! But the mighty dragon stalks our land again. He breathes vile, sulfurous fumes, poisoning all! His scales glow as hot as flames, and he brings death without mercy. Even the King was not safe from the dragon's wrath. Bálormr has sacked the King's keep in Deira and captured the King's young daughter. Now the kingdom teeters on the edge of death, and the people of Durham wastes away as the dragon ravages the land.")

  (println "---------------------------------")

  (println "Yet all hope is not lost, and a lone rider has appeared, carrying the sign of the dragon. The rider has arrived at the entrance of the King's castle in Deira, which now lay deserted. The rider slowly dismounts and enters the castle from the drawbridge. You are the rider. It is up to you to save the land of Durham and restore peace and harmony once again!")

  (println "---------------------------------")

  (loop [local-map the-map local-player adventurer] ;; basically a way to loop
    ;;recursively

      (let [pl (status local-player)
            _  (println "What do you want to do?")
            _  (println "---------------------------------")
            command (read-line)]
       (if (= local-player :done true)
          (println "Goodbye!")
          (recur local-map (respond pl (to-keywords command)))))))  ;; jump back to a loop thing with updated info
                               ;; may need to retool to capture additional detail
                               ;; such as tools and whatnot
