<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Money extends JsonObject
{
    const FIELD_CENT_AMOUNT = 'centAmount';
    const FIELD_CURRENCY_CODE = 'currencyCode';

    /**
     * @return string
     */
    public function getCentAmount();

    /**
     * @return string
     */
    public function getCurrencyCode();

    /**
     * @param string $centAmount
     * @return $this
     */
    public function setCentAmount(string $centAmount);

    /**
     * @param string $currencyCode
     * @return $this
     */
    public function setCurrencyCode(string $currencyCode);

}
