package ninja.haxing.jping;

public enum ConnectionResult {
    SUCCESS,
    FAILURE_TIMEOUT,
    FAILURE_UNKNOWN_HOST,
    FAILURE_UNREACHABLE,
    FAILURE_IO,
    FAILURE_MODEM_STATUS_UNREACHABLE
}
