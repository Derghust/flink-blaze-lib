package com.github.derghust.flinkblaze.util.status

/** Return status used for returning one of state such as OK, Error,...
  */
sealed trait ReturnStatus {
  val message: String
}

/** OK return state, used for state where is everything OK and working.
  *
  * @param message
  *   [[String]] status message for status OK default message is empty string.
  */
final case class RSOK(override val message: String = "") extends ReturnStatus

/** Warn return state, used for returnable and non fatal state.
  *
  * @param message
  *   [[String]] status message describing warning.
  */
final case class RSWarn(override val message: String) extends ReturnStatus

/** Error return state, used for returnable and non fatal state with [[Throwable]].
  *
  * @param message
  *   [[String]] status message describing error.
  * @param throwable
  *   [[Throwable]] error caught within process.
  */
final case class RSError(
    override val message: String,
    throwable: Throwable
) extends ReturnStatus

/** Fatal return state, used for non returnable and fatal state with [[Throwable]].
  *
  * @param message
  *   [[String]] status message describing fatal.
  * @param throwable
  *   [[Throwable]] error caught within process.
  */
final case class RSFatal(
    override val message: String,
    throwable: Throwable
) extends ReturnStatus
