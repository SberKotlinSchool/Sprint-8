package com.example.retailer.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DataItem(
    @Id
    @GeneratedValue
    var id: Long? = null,
    @Column
    var name: String
)