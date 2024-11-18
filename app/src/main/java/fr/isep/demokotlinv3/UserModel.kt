package fr.isep.demokotlinv3.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserModel : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var isActive: Boolean = false
    var age: Int = 0
}
