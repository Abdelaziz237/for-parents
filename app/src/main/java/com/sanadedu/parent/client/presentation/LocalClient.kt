package com.sanadedu.parent.client.presentation

object LocalClient {
    private lateinit var _clientViewModel: ClientViewModel

    fun initialize(clientViewModel: ClientViewModel) {
        _clientViewModel = clientViewModel
    }

    fun getClientViewModel(): ClientViewModel {
        if (!LocalClient::_clientViewModel.isInitialized) {
            throw IllegalStateException("ClientViewModel is not initialized. Call initialize() first.")
        }
        return _clientViewModel
    }
}