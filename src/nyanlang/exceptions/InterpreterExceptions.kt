class FileSuffixNotValidException(current: String, valid: String)
    : Exception("File suffix should be .$valid, not .$current")
class SyntaxError(message: String)
    : Exception(message)
class UnexpectedParserError(message: String)
    : Exception(message)