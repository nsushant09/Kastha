package com.neupanesushant.kasthabackend.data.model

import com.neupanesushant.kasthabackend.utils.constants.Table
import jakarta.persistence.*

@Entity
@jakarta.persistence.Table(name = Table.USER)
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,
    val firstName : String,
    val lastName : String,
    @Column(unique = true)
    val email : String,
    val password : String,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = Table.USER_ROLE,
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles : List<Role>
)