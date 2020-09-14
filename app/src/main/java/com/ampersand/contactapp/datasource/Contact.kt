package com.ampersand.contactapp.datasource

import android.content.ContentProviderOperation
import android.provider.ContactsContract

class PhoneBook {

    private fun addContact(
        firstName: String,
        lastName: String,
        modileNumber: String,
        emialID: String,
        company: String,
        jobTitle: String
    ) {

        val ops = ArrayList<ContentProviderOperation>()
        ops.add(setup())
        ops.add(addFirstName(firstName))
        ops.add(addLastName(lastName))
        ops.add(addPhoneNumber(mobileNumber))
        ops.add(addEmail(email))
        ops.add(addCompanyAndJob(company, job))

    }

    fun setup(): ContentProviderOperation {

        return ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .build()
    }

    fun addName(firstName: String, whichName: String): ContentProviderOperation {

        return ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            .withValue(whichName, firstName)
            .build()
    }

    fun addFirstName(firstName: String): ContentProviderOperation {

        return addName(
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
            firstName
        )
    }

    fun addLastName(lastName: String): ContentProviderOperation {

        return addName(
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            lastName
        )
    }

    fun addPhoneNumber(mobileNumber: String): ContentProviderOperation {

        return ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
            .withValue(
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
            )
            .build()
    }

    fun addEmail(email: String): ContentProviderOperation {

        return ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
            .withValue(
                ContactsContract.CommonDataKinds.Email.TYPE,
                ContactsContract.CommonDataKinds.Email.TYPE_WORK
            )
            .build()
    }

    fun addCompanyAndJob(company: String, title: String): ContentProviderOperation {

        return ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
            .withValue(
                ContactsContract.CommonDataKinds.Organization.TYPE,
                ContactsContract.CommonDataKinds.Organization.TYPE_WORK
            )
            .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
            .withValue(
                ContactsContract.CommonDataKinds.Organization.TYPE,
                ContactsContract.CommonDataKinds.Organization.TYPE_WORK
            )
            .build()
    }

}