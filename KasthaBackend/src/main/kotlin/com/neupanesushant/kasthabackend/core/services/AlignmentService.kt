package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.AlignmentRepo
import com.neupanesushant.kasthabackend.data.model.Alignment
import com.neupanesushant.kasthabackend.data.model.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AlignmentService(
    @Autowired private val alignmentRepo: AlignmentRepo
) {
    fun insert(alignment: Alignment) = alignmentRepo.save(alignment)
    fun update(alignment: Alignment) = alignmentRepo.save(alignment)
    fun delete(alignment: Alignment) = alignmentRepo.delete(alignment)
    fun ofId(id: Int): Optional<Alignment> = alignmentRepo.findById(id)

    val all: List<Alignment>
        get() {
            return alignmentRepo.findAll()
        }
}