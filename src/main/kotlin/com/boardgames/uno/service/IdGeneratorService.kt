package com.boardgames.uno.service

import com.boardgames.uno.domain.ID_SEQUENCE_COLLECTION
import com.boardgames.uno.domain.IdSequence
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

enum class IdType(val length: Int) {
    DummyUserId(10),
    UserId(5),
    GameId(10)
}

@Service
class IdGeneratorService(
    @Autowired
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {

    fun generateId(idType: IdType): Mono<String> {
        return reactiveMongoTemplate.findAndModify(
            Query.query(Criteria.where("_idType").`is`(idType)),
            Update().inc("sequence", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            IdSequence::class.java,
            ID_SEQUENCE_COLLECTION
        ).map { idSequence ->
            idSequence.sequence.toString().padStart(idType.length, '0')
        }
    }
}
