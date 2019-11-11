package Data;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.Collection;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableSet;

/**
 * Forms a bridge between ObservableSets and ObservableLists. When an object is added or removed from the set, the list
 * is also updated. This ensures that the ObservableList works as a set (i.e. no duplicates are stored), but that it
 * can also be used to populate a ListView or TableView.
 *
 * @param <T> The type of data to be stored
 */
public class ObservableSetList<T> {

    private ObservableSet<T> set = observableSet();

    private ObservableList<T> list = observableArrayList();


    /**
     * Construct the Set and List initialized with the data in the given collection.
     *
     * @param s The data with which to initialize the set and list
     */
    public ObservableSetList(Collection<T> s) {
        set.addListener((SetChangeListener<T>) c -> {
            // If an object is added to the set, add it to the list
            if (c.wasAdded()) {
                list.add(c.getElementAdded());
            }
            // If an object is removed from the set, remove it from the list
            if (c.wasRemoved()) {
                list.remove(c.getElementRemoved());
            }
        });

        set.addAll(s);
    }

    /**
     * Add all items in c to the observable set (and consequently the observable list)
     *
     * @param c The collection of items to be added
     */
    void addAll(Collection<T> c) {
        set.addAll(c);
    }

    /**
     * Returns the observable set of objects which ensures no duplicates are stored in the list
     *
     * @return the ObservableSet of objects
     */
    public ObservableSet<T> getSet() {
        return set;
    }

    /**
     * Returns the observable list of objects to be displayed by the ListView
     *
     * @return the Observable List of objects
     */
    public ObservableList<T> getList() {
        return list;
    }

    /**
     * Remove an element from the set and list
     *
     * @param t the element to be removed
     */
    void remove(T t) {
        set.remove(t);
    }
}

