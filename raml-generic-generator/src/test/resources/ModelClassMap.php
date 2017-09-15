<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\ClassMapModel;
use Test\Base\Collection;
use Test\Base\JsonCollection;
use Test\Base\JsonObject;
use Test\Base\JsonObjectModel;

class ModelClassMap extends ClassMapModel
{
    protected static $types = [
        JsonObject::class => JsonObjectModel::class,
        Collection::class => JsonCollection::class,
        Address::class => AddressModel::class,
        Customer::class => CustomerModel::class,

    ];
}
