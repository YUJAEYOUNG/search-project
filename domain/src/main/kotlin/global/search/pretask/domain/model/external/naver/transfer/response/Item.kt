package global.search.pretask.domain.model.external.naver.transfer.response

data class Item(
    val title: String,
    val link: String,
    val description: String,
    val bloggername: String,
    val bloggerlink: String,
    val postdate: String
)